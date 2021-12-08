package io.github.daniil547.domain;

import java.util.Set;

public interface PartiallyCopyable<T> {
    T copyPartially(Set<String> fieldsToCopy);
    T copyPartially(String newName, Set<String> fieldsToCopy);
    Set<String> getFieldsNames();
}
