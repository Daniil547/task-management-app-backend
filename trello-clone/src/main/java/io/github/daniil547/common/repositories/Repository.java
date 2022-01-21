package io.github.daniil547.common.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<E, ID> {
    E insert(E entity);

    E update(E entity);

    Optional<E> fetchById(ID id);

    List<E> fetchAll();

    void deleteById(ID id);
}
