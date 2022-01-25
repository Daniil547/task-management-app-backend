package io.github.daniil547.board;

import io.github.daniil547.board.label.Label;
import io.github.daniil547.card_list.CardList;
import io.github.daniil547.common.domain.Page;
import io.github.daniil547.user.member.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Board extends Page {
    private UUID workspaceId;

    private BoardVisibility visibility = BoardVisibility.PRIVATE;
    private Boolean active = true;
    private Set<Member> members = new HashSet<>();
    private List<Label> allLabels = new ArrayList<>();
    private List<CardList> cardLists = new ArrayList<>();
}
