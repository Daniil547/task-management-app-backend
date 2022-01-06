package io.github.daniil547.card_list;

import io.github.daniil547.card.Card;
import io.github.daniil547.common.domain.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CardList extends Resource {
    private List<Card> cards;
    private Boolean active = true;


    /*TODO: other methods*/
    /*TODO: implement*/
}
