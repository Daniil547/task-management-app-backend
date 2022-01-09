package io.github.daniil547.common.repositories;

import java.util.List;

public interface Repository<E, ID> {
    void insert(E entity) throws Exception;

    void update(E entity) throws Exception;

    E fetchById(ID id) throws Exception;

    List<E> fetchAll() throws Exception;

    void deleteById(ID id) throws Exception;
}
