package io.github.daniil547.domain;

import java.util.List;
import java.util.Set;

public class Board extends NestedPage
                   implements Copyable<Board>,
                              PartiallyCopyable<Board> {
    private BoardVisibility visibility = BoardVisibility.PRIVATE;
    private Boolean active = true;
    private Set<Member> members;
    private List<Label> allLabels;
    private List<CardList> cardLists;

    /*TODO: accessors*/
    /*TODO: constructors*/
    /*TODO: other methods*/
    /*TODO: implement*/
}
