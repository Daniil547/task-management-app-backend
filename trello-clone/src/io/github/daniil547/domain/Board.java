package io.github.daniil547.domain;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Board extends NestedPage
        implements Copyable<Board>,
        PartiallyCopyable<Board> {
    private BoardVisibility visibility = BoardVisibility.PRIVATE;
    private Boolean active = true;
    private Set<Member> members;
    private List<Label> allLabels;
    private List<CardList> cardLists;

    /*TODO: other methods*/
    /*TODO: implement*/
}
