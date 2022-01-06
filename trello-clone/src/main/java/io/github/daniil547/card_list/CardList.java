package io.github.daniil547.card_list;

import io.github.daniil547.card.Card;
import io.github.daniil547.common.domain.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CardList extends Page {
    private List<Card> cards;
    private Boolean active = true;


    /*TODO: other methods*/
    /*TODO: implement*/
}
