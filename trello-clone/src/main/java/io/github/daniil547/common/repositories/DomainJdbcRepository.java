package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Domain;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public abstract class DomainJdbcRepository<E extends Domain> implements Repository<E, UUID> {
    protected final DataSource dataSource;

    List<String> columns = new ArrayList<>();

    protected final String insertQuery =
            """
            INSERT INTO %s
                (%s)
            VALUES
                (%s)"""
                    .formatted(
                            getTableName(),
                            commifyList(columns),
                            parameterList(columns.size())
                              );
    protected final String updateQuery =
            """
            UPDATE %s
            SET (%s) =
                (%s)
            WHERE id = ?"""
                    .formatted(
                            getTableName(),
                            commifyList(columns.subList(1, columns.size())),
                            parameterList(columns.size() - 1)
                              );
    protected final String selectByIdQuery = """
                                             SELECT * FROM %s
                                             WHERE id = ?""".formatted(getTableName());
    protected final String selectAllQuery = """
                                            SELECT * FROM %s""".formatted(getTableName());
    protected final String deleteByIdQuery = """
                                             DELETE FROM %s
                                             WHERE id = ?""".formatted(getTableName());

    protected DomainJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;

        columns.addAll(getCommonColumns());
        columns.addAll(getEntitySpecificColumns());
        tweakColumnNames(columns);
    }

    Integer fillCommonQueryParams(E entity, PreparedStatement stmnt, Boolean idIsLast) throws SQLException {
        int index = idIsLast ? stmnt.getParameterMetaData().getParameterCount() - 1 : 1;
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

        return res.substring(0, res.length() - 3); //excludes the last whitespace and comma
    }

    @Override
    public void insert(E entity) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(insertQuery)) {
            int startingWith = fillCommonQueryParams(entity, stmnt, false);
            fillEntitySpecificQueryParams(entity, stmnt, startingWith);

            stmnt.execute();
        }
    }

    @Override
    public void update(E entity) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(updateQuery)) {
            int startingWith = fillCommonQueryParams(entity, stmnt, true);
            fillEntitySpecificQueryParams(entity, stmnt, startingWith);

            stmnt.execute();
        }
    }

    @Override
    public E fetchById(UUID uuid) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(selectByIdQuery)) {
            stmnt.setObject(1, uuid);

            ResultSet resultSet = stmnt.executeQuery();
            resultSet.next();
            E resEntity = fillEntitySpecificFields(resultSet);
            fillCommonEntityFields(resEntity, resultSet);

            return resEntity;
        }
    }

    @Override
    public List<E> fetchAll() throws SQLException {
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
        }
    }

    @Override
    public void deleteById(UUID uuid) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(deleteByIdQuery)) {
            stmnt.setObject(1, uuid);
            stmnt.execute();
        }
    }
}
