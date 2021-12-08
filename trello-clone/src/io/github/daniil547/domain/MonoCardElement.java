package io.github.daniil547.domain;

public interface MonoCardElement<T> {
    T getContent();
    void setContent(T content);
    String getName();
    void setName(String name);

}
