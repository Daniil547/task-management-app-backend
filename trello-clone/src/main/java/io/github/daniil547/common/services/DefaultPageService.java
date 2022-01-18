package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.repositories.PageRepository;

public abstract class DefaultPageService<E extends Page> extends DefaultDomainService<E> implements PageService<E> {

    protected E init(E entity, String pageName, String title, String descr) {
        super.initEntity(entity);

        entity.setPageName(pageName);
        entity.setTitle(title);
        entity.setDescription(descr);

        return entity;
    }

    @Override
    protected abstract PageRepository<E> getRepository();
}
