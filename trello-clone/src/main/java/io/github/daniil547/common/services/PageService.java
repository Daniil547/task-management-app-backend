package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.repositories.PageRepository;

public interface PageService<D extends PageDto, E extends Page> extends DomainService<D, E> {
    @Override
    PageRepository<E> repository();
}
