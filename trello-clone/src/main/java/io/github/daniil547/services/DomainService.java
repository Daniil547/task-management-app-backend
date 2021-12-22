package io.github.daniil547.services;

import io.github.daniil547.domain.Domain;

public abstract class DomainService<T extends Domain> {
    static long uuid = 0;

    public T create(T inst) {
        inst.setId(++uuid);
        return inst;
    }

    public String toStringOf(T inst) {
        return inst.toString();
    }

}
