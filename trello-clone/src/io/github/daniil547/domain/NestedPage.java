package io.github.daniil547.domain;

import lombok.Data;

@Data
public abstract class NestedPage {
    private String title;
    private String name;
    private String description;
}
