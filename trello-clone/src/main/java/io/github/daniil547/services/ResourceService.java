package io.github.daniil547.services;

import io.github.daniil547.domain.Member;
import io.github.daniil547.domain.Resource;

import java.time.ZonedDateTime;

public abstract class ResourceService<T extends Resource> extends DomainService<T> {
    public T create(T inst, Member creator, String page_name, String title, String descr) {
        super.create(inst);
        inst.setCreatedWhen(ZonedDateTime.now());
        inst.setCreatedBy(creator);
        inst.setName(page_name);
        inst.setTitle(title);
        inst.setDescription(descr);
        return inst;
    }
}
