package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface DomainRepository<E extends Domain> extends JpaRepository<E, UUID>,
                                                            JpaSpecificationExecutor<E> {
    default E insert(E entity) {
        return save(entity);
    }

    default E update(E entity) {
        return save(entity);
    }
}
