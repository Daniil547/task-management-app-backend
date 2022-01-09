package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Domain;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class DomainSqlRepository<E extends Domain> implements Repository<E, UUID> {
    protected final DataSource dataSource;

    protected String insertQuery =
            """
            INSERT INTO %s
                (%s)
            VALUES
                (%s)"""
                    .formatted(
                            getTableName(),
                            commifyList(getColumns()),
                            parameterList(getColumns().size())
                              );
    protected String updateQuery =
            """
            UPDATE %s
            SET (%s) =
                (%s)
            WHERE id = ?"""
                    .formatted(
                            getTableName(),
                            commifyList(getColumns().subList(1, 9999)),
                            parameterList(getColumns().size() - 1)
                              );
    protected final String selectByIdQuery = """
                                             SELECT * FROM %s
                                             WHERE id = ?""".formatted(getTableName());
    protected final String selectAllQuery = """
                                            SELECT * FROM %s""".formatted(getTableName());
    protected final String deleteByIdQuery = """
                                             DELETE FROM %s
                                             WHERE id = ?""".formatted(getTableName());

    protected DomainSqlRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void fillDomainQueryParams(E domainEntity, PreparedStatement stmnt, Boolean last) throws SQLException {
        int index = last ? stmnt.getParameterMetaData().getParameterCount() - 1 : 1;
        stmnt.setObject(index, domainEntity.getId());
    }

    void fillDomainEntityFields(E entity, ResultSet resultSet) throws SQLException {
        entity.setId(resultSet.getObject("id", UUID.class));
    }

    protected abstract String getTableName();

    protected abstract List<String> getColumns();

    protected abstract void fillEntitySpecificQueryParams(E entity, PreparedStatement stmnt, Integer startingWith) throws SQLException;

    protected abstract E fillEntitySpecificFields(ResultSet resultSet) throws SQLException;

    private <T> String commifyList(List<T> elements) {
        StringBuilder tmp = new StringBuilder();

        for (Iterator<T> it = elements.iterator(); it.hasNext(); ) {
            tmp.append(it.next());
            if (it.hasNext()) {
                tmp.append(", ");
            }
        }

        return tmp.toString();
    }

    private String parameterList(Integer parametersCount) {
        StringBuilder tmp = new StringBuilder();

        for (int i = 0; i < parametersCount; i++) {
            if (i < parametersCount - 1) {
                tmp.append(", ?");
            }
        }

        return tmp.toString();
    }

    @Override
    public void insert(E entity) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(insertQuery)) {
            fillDomainQueryParams(entity, stmnt, false);
            fillEntitySpecificQueryParams(entity, stmnt, 2);

            stmnt.execute();
        }
    }

    @Override
    public void update(E entity) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(updateQuery)) {
            fillDomainQueryParams(entity, stmnt, true);
            fillEntitySpecificQueryParams(entity, stmnt, 1);

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
            fillDomainEntityFields(resEntity, resultSet);

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
                fillDomainEntityFields(tmpEntity, resultSet);
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
