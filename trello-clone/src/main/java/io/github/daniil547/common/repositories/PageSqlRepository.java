package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Page;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class PageSqlRepository<E extends Page> extends DomainSqlRepository<E> {

    protected PageSqlRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insert(E pageEntity) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(insertQuery)) {
            fillDomainQueryParams(pageEntity, stmnt, false);
            fillPageQueryParams(pageEntity, stmnt, 2);

            stmnt.execute();
        }
    }

    @Override
    public void update(E pageEntity) throws SQLException {
        try (PreparedStatement stmnt = dataSource.getConnection().prepareStatement(updateQuery)) {
            fillDomainQueryParams(pageEntity, stmnt, true);
            fillPageQueryParams(pageEntity, stmnt, 1);

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
            fillPageEntityFields(resEntity, resultSet);

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
                fillPageEntityFields(tmpEntity, resultSet);
                resEntities.add(tmpEntity);
            }

            return resEntities;
        }
    }

    private void fillPageEntityFields(E pageEntity, ResultSet resultSet) throws SQLException {
        pageEntity.setTitle(resultSet.getString("page_title"));
        pageEntity.setPageName(resultSet.getString("page_name"));
        pageEntity.setDescription(resultSet.getString("page_description"));
    }

    private void fillPageQueryParams(E pageEntity, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setString(startingWith, pageEntity.getTitle());
        stmnt.setString(startingWith + 1, pageEntity.getPageName());
        stmnt.setString(startingWith + 2, pageEntity.getDescription());
    }
}
