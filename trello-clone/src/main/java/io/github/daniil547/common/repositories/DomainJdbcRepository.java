package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.repositories.utils.CaseInsensitiveBeanPropertySqlParameterSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
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
 * <p>
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
                        parameterList(columns)
                          );
        this.updateQuery = """
                           UPDATE %s
                           SET (%s) =
                               (%s)
                           WHERE id = :%s"""
                .formatted(
                        getTableName(),
                        commifyList(columns.subList(1, columns.size())),
                        parameterList(columns.subList(1, columns.size())),
                        columns.get(0)
                          );
        this.selectByIdQuery = """
                               SELECT * FROM %s
                               WHERE id = :id"""
                .formatted(getTableName());
        this.selectAllQuery = """
                              SELECT * FROM %s"""
                .formatted(getTableName());
        this.deleteByIdQuery = """
                               DELETE FROM %s
                               WHERE id = :id"""
                .formatted(getTableName());
    }

    List<String> getCommonColumns() {
        return List.of("id");
    }

    protected void tweakColumnNames(List<String> columns) {
        //do nothing by default
    }

    protected abstract String getTableName();

    protected abstract List<String> getEntitySpecificColumns();

    private String commifyList(List<String> elements) {
        String res = String.join(",", elements);

        return res;
    }

    private String parameterList(List<String> elements) {
        StringBuilder res = new StringBuilder();
        for (String element : elements) {
            res.append(":").append(element).append(", ");
        }

        return res.substring(0, res.length() - 2); //excludes the last whitespace and comma
    }

    @Override
    public E insert(E entity) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        CaseInsensitiveBeanPropertySqlParameterSource parameterSource = new CaseInsensitiveBeanPropertySqlParameterSource(entity);
        template.update(insertQuery, parameterSource);

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
    }

    @Override
    public E update(E entity) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);

        int rowsUpdated = template.update(updateQuery, new CaseInsensitiveBeanPropertySqlParameterSource(entity));

        if (rowsUpdated == 0) {
            throw new IllegalStateException("update failed: no entity with given ID found");
        }

        //in case there was any mutation on the DB side.
        //we updated already inserted value, so it shouldn't be empty
        E actuallyPersisted = fetchById(entity.getId()).get();
        return actuallyPersisted;
    }

    @Override
    public Optional<E> fetchById(UUID uuid) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        //this is ugly but BeanPropertyRowMapper<E> can't understand that it is expected to map to E and requires a Class<E> instance :facepalm:
        Class<E> classOfE = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        E entity;
        try {
            entity = template.queryForObject(selectByIdQuery,
                                             new MapSqlParameterSource("id", uuid),
                                             new BeanPropertyRowMapper<E>(classOfE));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        return Optional.of(entity);
    }

    @Override
    public List<E> fetchAll() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        //this is ugly but BeanPropertyRowMapper<E> can't understand that it is expected to map to E and requires a Class<E> instance :facepalm:
        Class<E> classOfE = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        List<E> entities = template.query(selectAllQuery, new BeanPropertyRowMapper<E>(classOfE));
        return entities;
    }

    @Override
    public void deleteById(UUID uuid) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        template.update(deleteByIdQuery, new MapSqlParameterSource("id", uuid));
    }
}
