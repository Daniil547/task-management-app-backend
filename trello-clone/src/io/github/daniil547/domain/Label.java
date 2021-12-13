package io.github.daniil547.domain;

import lombok.Data;

@Data
public class Label extends Domain {
    private String name;
    private int color; //int is better than String

}
