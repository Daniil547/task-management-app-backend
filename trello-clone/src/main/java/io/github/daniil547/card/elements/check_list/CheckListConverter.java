package io.github.daniil547.card.elements.check_list;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckListConverter extends DomainConverter<CheckListDto, CheckList> {
    private final CheckableItemConverter checkableItemConverter;

    @Autowired
    public CheckListConverter(CheckableItemConverter checkableItemConverter) {
        this.checkableItemConverter = checkableItemConverter;
    }

    @Override
    protected CheckList transferEntitySpecificFieldsFromDto(CheckListDto dto) {
        CheckList checkList = new CheckList();

        checkList.setCardId(dto.getCardId());
        checkList.setName(dto.getName());
        checkList.setPosition(dto.getPosition());
        checkList.setItems(dto.getItemDtos()
                              .stream()
                              .map(checkableItemConverter::entityFromDto)
                              .toList());

        return checkList;
    }

    @Override
    protected CheckListDto transferDtoSpecificFieldsFromEntity(CheckList entity) {
        CheckListDto dto = new CheckListDto();

        dto.setCardId(entity.getCardId());
        dto.setName(entity.getName());
        dto.setPosition(entity.getPosition());
        dto.setItemDtos(entity.getItems()
                              .stream()
                              .map(checkableItemConverter::dtoFromEntity)
                              .toList());

        return dto;
    }
}
