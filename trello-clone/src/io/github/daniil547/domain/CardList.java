package io.github.daniil547.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CardList extends NestedPage
        implements Copyable<CardList>,
        PartiallyCopyable<CardList> {
    private List<Card> cards;
    private Boolean active = true;


    /*TODO: other methods*/
    /*TODO: implement*/
}
