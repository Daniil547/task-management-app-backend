package io.github.daniil547.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CheckList<E> extends Domain/* implements ListableCardElement<CheckableItem>,
        Copyable<CheckList> */ {
    private String name;
    private List<CheckableItem> items;

    /*TODO: implement*/
}
