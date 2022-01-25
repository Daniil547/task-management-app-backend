package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Page;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension of {@link DomainJdbcRepository} for Page entities.
 * Adds their common fields.
 *
 * @param <E> - entity class to persist
 */
public abstract class PageJdbcRepository<E extends Page> extends DomainJdbcRepository<E> implements PageRepository<E> {

    protected PageJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    List<String> getCommonColumns() {
        List<String> commonColumns = new ArrayList<>();
        commonColumns.addAll(super.getCommonColumns());

        commonColumns.addAll(List.of("page_title", "page_name", "page_description"));

        return commonColumns;
    }
}
