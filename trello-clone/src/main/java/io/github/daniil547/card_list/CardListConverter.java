package io.github.daniil547.card_list;

import io.github.daniil547.common.services.PageConverter;
import org.springframework.stereotype.Component;

@Component
public class CardListConverter extends PageConverter<CardListDto, CardList> {

    @Override
    protected CardList transferEntitySpecificFieldsFromDto(CardListDto dto) {
        CardList cardList = new CardList();

        cardList.setBoardId(dto.getBoardId());
        cardList.setPosition(dto.getPosition());
        cardList.setActive(dto.getActive());

        return cardList;
    }

    @Override
    protected CardListDto transferDtoSpecificFieldsFromEntity(CardList entity) {
        CardListDto dto = new CardListDto();

        dto.setBoardId(entity.getBoardId());
        dto.setPosition(entity.getPosition());
        dto.setActive(entity.getActive());

        return dto;
    }
}