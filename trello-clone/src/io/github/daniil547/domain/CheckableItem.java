package io.github.daniil547.domain;

import lombok.Data;

@Data
public class CheckableItem extends Domain {
    private Boolean checked = false;
    private String description;
}
