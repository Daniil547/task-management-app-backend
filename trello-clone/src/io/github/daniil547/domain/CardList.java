package io.github.daniil547.domain;

import lombok.Data;

import java.util.List;

@Data
public class CardList extends NestedPage
        implements Copyable<CardList>,
        PartiallyCopyable<CardList> {
    private List<Card> cards;
    private Boolean active = true;


    /*TODO: other methods*/
    /*TODO: implement*/
}
