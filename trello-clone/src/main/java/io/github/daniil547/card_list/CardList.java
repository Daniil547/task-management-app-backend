package io.github.daniil547.card_list;

import io.github.daniil547.card.Card;
import io.github.daniil547.common.domain.Page;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardList extends Page {
    private UUID boardId;
    private Integer position;

    private List<Card> cards;
    private Boolean active = true;
}
