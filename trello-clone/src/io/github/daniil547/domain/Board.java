package io.github.daniil547.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
