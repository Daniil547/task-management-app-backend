package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.repositories.PageRepository;

public abstract class DefaultPageService<D extends PageDto, E extends Page> extends DefaultDomainService<D, E> implements PageService<D, E> {

    public DefaultPageService() {
        super();
    }

    @Override
    public abstract PageRepository<E> repository();

}
