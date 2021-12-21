package io.github.daniil547.services;

import io.github.daniil547.domain.Member;
import io.github.daniil547.domain.NestedPage;

public abstract class NestedPageService<T extends NestedPage> extends ResourceService<T> {
    public T create(T inst, Member creator, String title) {
        super.create(inst, creator);
        inst.setTitle(title);
        // create a default name
        inst.setName(title + inst.getId());
        return inst;
    }
}
