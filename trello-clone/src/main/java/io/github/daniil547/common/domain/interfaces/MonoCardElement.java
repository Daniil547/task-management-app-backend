package io.github.daniil547.common.domain.interfaces;

public interface MonoCardElement<T> {
    T getContent();
    void setContent(T content);
    String getName();
    void setName(String name);

}
