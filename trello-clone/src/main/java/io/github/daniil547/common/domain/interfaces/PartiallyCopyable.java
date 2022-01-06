package io.github.daniil547.common.domain.interfaces;

import java.util.Set;

public interface PartiallyCopyable<T> {
    T copyPartially(Set<String> fieldsToCopy);
    T copyPartially(String newName, Set<String> fieldsToCopy);
    Set<String> getFieldsNames();
}
