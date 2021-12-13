package io.github.daniil547.domain;

import lombok.Data;

@Data
public class CheckableItem {
    private Boolean checked = false;
    private String description;
}
