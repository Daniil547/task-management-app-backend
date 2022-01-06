package io.github.daniil547.board;

import io.github.daniil547.board.label.Label;
import io.github.daniil547.card_list.CardList;
import io.github.daniil547.common.domain.Resource;
import io.github.daniil547.user.member.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Board extends Resource {
    private BoardVisibility visibility = BoardVisibility.PRIVATE;
    private Boolean active = true;
    private Set<Member> members;
    private List<Label> allLabels;
    private List<CardList> cardLists;

    /*TODO: other methods*/
    /*TODO: implement*/
}
