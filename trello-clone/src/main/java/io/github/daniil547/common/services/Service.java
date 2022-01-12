package io.github.daniil547.common.services;

import java.util.List;

public interface Service<E, ID> {

    void save(E entity) throws Exception;

    void update(E entity) throws Exception;

    E getById(ID id) throws Exception;

    List<E> getAll() throws Exception;

    void deleteById(ID id) throws Exception;
}
