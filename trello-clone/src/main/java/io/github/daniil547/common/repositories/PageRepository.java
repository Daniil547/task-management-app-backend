package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface PageRepository<E extends Page> extends DomainRepository<E> {
    Optional<E> findByPageName(String pageName);

    void deleteByPageName(String pageName);
}
