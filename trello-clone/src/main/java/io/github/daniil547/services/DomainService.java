package io.github.daniil547.services;

import io.github.daniil547.domain.Domain;

import java.util.UUID;

public abstract class DomainService<T extends Domain> {

    public T create(T inst) {
        inst.setId(UUID.randomUUID());
        return inst;
    }

    public String toStringOf(T inst) {
        return inst.toString();
    }

}
