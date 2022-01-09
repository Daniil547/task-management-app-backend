package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;

public abstract class PageService<T extends Page> extends DomainService<T> {
    public T create(T inst, String pageName, String title, String descr) {
        super.create(inst);
        inst.setPageName(pageName);
        inst.setTitle(title);
        inst.setDescription(descr);
        return inst;
    }
}
