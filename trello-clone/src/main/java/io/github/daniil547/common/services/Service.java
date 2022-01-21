package io.github.daniil547.common.services;

import java.util.List;
import java.util.Optional;

public interface Service<E, ID> {

    E save(E entity);

    E update(E entity);

    Optional<E> getById(ID id);

    List<E> getAll();

    void deleteById(ID id);
}
