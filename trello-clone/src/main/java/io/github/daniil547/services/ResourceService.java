package io.github.daniil547.services;

import io.github.daniil547.domain.Member;
import io.github.daniil547.domain.Resource;

import java.time.ZonedDateTime;

public abstract class ResourceService<T extends Resource> extends DomainService<T> {
    public T create(T inst, Member creator) {
        super.create(inst);
        inst.setCreatedWhen(ZonedDateTime.now());
        inst.setCreatedBy(creator);
        return inst;
    }
}
