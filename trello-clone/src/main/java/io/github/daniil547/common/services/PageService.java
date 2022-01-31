package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;

import java.util.Optional;

public interface PageService<E extends Page> extends DomainService<E> {

    Optional<E> getByPageName(String pageName);

    void deleteByPageName(String pageName);
}
