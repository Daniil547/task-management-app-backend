package io.github.daniil547.board.label;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Label extends Domain {
    private String name;
    private int color; //int is better than String

}
