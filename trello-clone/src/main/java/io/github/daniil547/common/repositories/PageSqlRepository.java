package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Page;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension of {@link DomainSqlRepository} for Page entities.
 * Adds their common fields.
 *
 * @param <E> - entity class to persist
 */
public abstract class PageSqlRepository<E extends Page> extends DomainSqlRepository<E> {

    protected PageSqlRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    Integer fillCommonQueryParams(E entity, PreparedStatement stmnt, Boolean idIsLast) throws SQLException {
        super.fillCommonQueryParams(entity, stmnt, idIsLast);

        int startingWith = idIsLast ? 1 : 2;

        stmnt.setString(startingWith, entity.getTitle());
        stmnt.setString(startingWith + 1, entity.getPageName());
        stmnt.setString(startingWith + 2, entity.getDescription());

        return startingWith + 3;
    }

    @Override
    void fillCommonEntityFields(E entity, ResultSet resultSet) throws SQLException {
        super.fillCommonEntityFields(entity, resultSet);

        entity.setTitle(resultSet.getString(columns.get(2)));
        entity.setPageName(resultSet.getString(columns.get(3)));
        entity.setDescription(resultSet.getString(columns.get(4)));
    }

    @Override
    List<String> getCommonColumns() {
        List<String> commonColumns = new ArrayList<>();
        commonColumns.addAll(super.getCommonColumns());

        commonColumns.addAll(List.of("page_title", "page_name", "page_description"));

        return commonColumns;
    }
}
