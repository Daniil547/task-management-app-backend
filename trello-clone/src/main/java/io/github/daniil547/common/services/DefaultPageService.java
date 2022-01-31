package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.repositories.PageRepository;

import java.util.Optional;

public abstract class DefaultPageService<E extends Page> extends DefaultDomainService<E> implements PageService<E> {

    protected E init(E entity, String pageName, String title, String descr) {
        super.initEntity(entity);

        entity.setPageName(pageName);
        entity.setPageTitle(title);
        entity.setPageDescription(descr);

        return entity;
    }

    @Override
    public Optional<E> getByPageName(String pageName) {
        return getRepository().fetchByPageName(pageName);
    }

    @Override
    public void deleteByPageName(String pageName) {
        getRepository().deleteByPageName(pageName);
    }

    @Override
    protected abstract PageRepository<E> getRepository();
}
