package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Domain;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * This is the main implementation of Repository that works with JDBC.
 * It contains all the common base behavior of concrete repositories and exposes abstract methods to configure it:<br><br>
 * <p>
 * {@link #getTableName()} of the implementing entity<br>
 * {@link #getEntitySpecificColumns()} of that table<br>
 * {@link #fillEntitySpecificFields(ResultSet)} into the entity itself<br>
 * {@link #fillEntitySpecificQueryParams(Domain, PreparedStatement, Integer)} in the query<br><br>
 * and optional<br>
 * {@link #tweakColumnNames(List)} - if some column names differ or new columns are used instead of default ones<br>
 *
 * @param <E> - entity class to persist
 */
public abstract class DomainJdbcRepository<E extends Domain> implements DomainRepository<E> {
    protected final DataSource dataSource;

    List<String> columns = new ArrayList<>();

    protected final String insertQuery;
    protected final String updateQuery;
    protected final String selectByIdQuery;
    protected final String selectAllQuery;
    protected final String deleteByIdQuery;

    protected DomainJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;

        columns.addAll(getCommonColumns());
        columns.addAll(getEntitySpecificColumns());
        tweakColumnNames(columns);

        this.insertQuery = """
                           INSERT INTO %s
                               (%s)
                           VALUES
                               (%s)"""
                .formatted(
                        getTableName(),
                        commifyList(columns),
                        parameterList(columns.size())
                          );
        this.updateQuery = """
                           UPDATE %s
                           SET (%s) =
                               (%s)
                           WHERE id = ?"""
                .formatted(
                        getTableName(),
                        commifyList(columns.subList(1, columns.size())),
                        parameterList(columns.size() - 1)
                          );
        this.selectByIdQuery = """
                               SELECT * FROM %s
                               WHERE id = ?"""
                .formatted(getTableName());
        this.selectAllQuery = """
                              SELECT * FROM %s"""
                .formatted(getTableName());
        this.deleteByIdQuery = """
                               DELETE FROM %s
                               WHERE id = ?"""
                .formatted(getTableName());
    }

    Integer fillCommonQueryParams(E entity, PreparedStatement stmnt, Boolean idIsLast) throws SQLException {
        int index = idIsLast ? stmnt.getParameterMetaData().getParameterCount() : 1;
        stmnt.setObject(index, entity.getId());
        return idIsLast ? 1 : 2;
    }

    void fillCommonEntityFields(E entity, ResultSet resultSet) throws SQLException {
        entity.setId(resultSet.getObject("id", UUID.class));
    }

    List<String> getCommonColumns() {
        return List.of("id");
    }

    protected void tweakColumnNames(List<String> columns) {
        //do nothing by default
    }

    protected abstract String getTableName();

    protected abstract List<String> getEntitySpecificColumns();

    protected abstract void fillEntitySpecificQueryParams(E entity, PreparedStatement stmnt, Integer startingWith) throws SQLException;

    protected abstract E fillEntitySpecificFields(ResultSet resultSet) throws SQLException;

    private String commifyList(List<String> elements) {
        String res = String.join(",", elements);

        return res;
    }

    private String parameterList(Integer parametersCount) {
        String res = "?, ".repeat(parametersCount);

        return res.substring(0, res.length() - 2); //excludes the last whitespace and comma
    }

    @Override
    public E insert(E entity) {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(insertQuery)) {
            int startingWith = fillCommonQueryParams(entity, stmnt, false);
            fillEntitySpecificQueryParams(entity, stmnt, startingWith);

            stmnt.execute();

            //in case there was some mutation on the DB side
            Optional<E> actuallyPersisted = fetchById(entity.getId());

            //we just inserted, so it shouldn't be empty
            //and if insertion failed, these lines wouldn't execute
            if (actuallyPersisted.isPresent()) {
                return actuallyPersisted.get();
            } else {
                //but if they actually do, there must be something really wrong
                throw new IllegalStateException("insert failed: immediate retrieval failed");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("insert failed", e);
        }
    }

    @Override
    public E update(E entity) {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(updateQuery)) {
            int startingWith = fillCommonQueryParams(entity, stmnt, true);
            fillEntitySpecificQueryParams(entity, stmnt, startingWith);

            int rowsUpdated = stmnt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new IllegalStateException("update failed: no entity with given ID found");
            }

            //in case there was any mutation on the DB side.
            //we updated already inserted value, so it shouldn't be empty
            E actuallyPersisted = fetchById(entity.getId()).get();
            return actuallyPersisted;
        } catch (SQLException e) {
            throw new IllegalStateException("update failed", e);
        }
    }

    @Override
    public Optional<E> fetchById(UUID uuid) {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(selectByIdQuery)) {
            stmnt.setObject(1, uuid);

            ResultSet resultSet = stmnt.executeQuery();
            if (resultSet.next()) {
                E resEntity = fillEntitySpecificFields(resultSet);
                fillCommonEntityFields(resEntity, resultSet);
                return Optional.of(resEntity);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new IllegalStateException("getchById failed", e);
        }
    }

    @Override
    public List<E> fetchAll() {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(selectAllQuery)) {
            ResultSet resultSet = stmnt.executeQuery();

            List<E> resEntities = new ArrayList<>();
            E tmpEntity;
            while (resultSet.next()) {
                tmpEntity = fillEntitySpecificFields(resultSet);
                fillCommonEntityFields(tmpEntity, resultSet);
                resEntities.add(tmpEntity);
            }

            return resEntities;
        } catch (SQLException e) {
            throw new IllegalStateException("fetchAll failed", e);
        }
    }

    @Override
    public void deleteById(UUID uuid) {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(deleteByIdQuery)) {
            stmnt.setObject(1, uuid);
            stmnt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("deleteById failed", e);
        }
    }
}
