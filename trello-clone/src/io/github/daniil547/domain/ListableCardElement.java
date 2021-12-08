package io.github.daniil547.domain;

import java.util.List;

public interface ListableCardElement<E> {
    E getEntry(int pos);
    void setEntry(int pos, E entry);
    List<E> getEntries();
    void setEntries(List<E> entries);
    String getName();
    void setName(String name);
}
