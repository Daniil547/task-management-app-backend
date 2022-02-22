package io.github.daniil547.common.controllers;

import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.dto.PageDto;
import io.github.daniil547.common.services.PageService;

public abstract class PageWebMvcController<D extends PageDto, E extends Page> extends DomainWebMvcController<D, E> {
    abstract public PageService<D, E> service();
}
