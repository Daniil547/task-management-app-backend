package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Page;

import java.util.Optional;

public interface PageRepository<E extends Page> extends DomainRepository<E> {
    Optional<E> fetchByPageName(String pageName);

    void deleteByPageName(String pageName);
}
