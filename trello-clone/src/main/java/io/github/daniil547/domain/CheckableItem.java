package io.github.daniil547.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CheckableItem extends Domain {
    private Boolean checked = false;
    private String description;
}
