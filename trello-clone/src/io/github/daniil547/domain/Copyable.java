package io.github.daniil547.domain;

public interface Copyable<T> {
    T copy();
    T copy(String newName);
}
