package io.github.daniil547;

import com.github.javafaker.Faker;
import io.github.daniil547.common.domain.Page;

public abstract class AbstractProvider<T extends Page> {
    protected final Faker faker = new Faker();

    public abstract T ensureExists();
}
