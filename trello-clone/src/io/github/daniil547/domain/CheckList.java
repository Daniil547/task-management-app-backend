package io.github.daniil547.domain;

import lombok.Data;

import java.util.List;

@Data
public class CheckList<E> implements ListableCardElement<CheckableItem>,
        Copyable<CheckList>{
    private String name;
    private List<CheckableItem> items;

    /*TODO: implement*/
}
