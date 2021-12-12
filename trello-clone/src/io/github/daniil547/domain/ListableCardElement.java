package io.github.daniil547.domain;

import java.util.List;

public interface ListableCardElement<E> {
    E getEntry(Integer pos);
    void setEntry(Integer pos, E entry);
    List<E> getEntries();
    void setEntries(List<E> entries);
    String getName();
    void setName(String name);
}
