package io.github.daniil547.card.elements;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class CheckableItemConverter extends DomainConverter<CheckableItemDto, CheckableItem> {
    @Override
    protected CheckableItem transferEntitySpecificFieldsFromDto(CheckableItemDto dto) {
        CheckableItem checkableItem = new CheckableItem();

        checkableItem.setCheckListId(dto.getCheckListId());
        checkableItem.setChecked(dto.getChecked());
        checkableItem.setDescription(dto.getDescription());
        checkableItem.setPosition(dto.getPosition());

        return checkableItem;
    }

    @Override
    protected CheckableItemDto transferDtoSpecificFieldsFromEntity(CheckableItem entity) {
        CheckableItemDto dto = new CheckableItemDto();

        dto.setCheckListId(entity.getCheckListId());
        dto.setChecked(entity.getChecked());
        dto.setDescription(entity.getDescription());
        dto.setPosition(entity.getPosition());

        return dto;
    }
}
