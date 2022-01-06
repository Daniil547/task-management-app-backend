package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Resource;
import io.github.daniil547.user.member.Member;

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
