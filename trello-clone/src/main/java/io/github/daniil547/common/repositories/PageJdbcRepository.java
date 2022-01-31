package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Page;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Extension of {@link DomainJdbcRepository} for Page entities.
 * Adds their common fields.
 *
 * @param <E> - entity class to persist
 */
public abstract class PageJdbcRepository<E extends Page> extends DomainJdbcRepository<E> implements PageRepository<E> {

    protected final String selectByPageNameQuery;
    private final String deleteByPageNameQuery;

    protected PageJdbcRepository(DataSource dataSource) {
        super(dataSource);
        this.selectByPageNameQuery = """
                                     SELECT * FROM %s
                                     WHERE page_name = :page_name"""
                .formatted(getTableName());
        this.deleteByPageNameQuery = """
                                     DELETE FROM %s
                                     WHERE page_name = :page_name"""
                .formatted(getTableName());
    }

    @Override
    List<String> getCommonColumns() {
        List<String> commonColumns = new ArrayList<>();
        commonColumns.addAll(super.getCommonColumns());

        commonColumns.addAll(List.of("page_title", "page_name", "page_description"));

        return commonColumns;
    }


    @Override
    public Optional<E> fetchByPageName(String pageName) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        Class<E> classOfE = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        E entity;
        try {
            entity = template.queryForObject(selectByPageNameQuery,
                                             new MapSqlParameterSource("page_name", pageName),
                                             new BeanPropertyRowMapper<E>(classOfE));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        return Optional.of(entity);
    }

    @Override
    public void deleteByPageName(String pageName) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        template.update(deleteByPageNameQuery, new MapSqlParameterSource("page_name", pageName));
    }
}
