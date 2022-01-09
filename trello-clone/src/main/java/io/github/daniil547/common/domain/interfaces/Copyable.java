package io.github.daniil547.common.domain.interfaces;

public interface Copyable<T> {
    T copy();
    T copy(String newName);
}
