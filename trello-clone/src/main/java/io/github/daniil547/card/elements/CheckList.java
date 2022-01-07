package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CheckList<E> extends Domain/* implements ListableCardElement<CheckableItem>,
        Copyable<CheckList> */ {
    private String name;
    private List<CheckableItem> items;

    /*TODO: implement*/
}
