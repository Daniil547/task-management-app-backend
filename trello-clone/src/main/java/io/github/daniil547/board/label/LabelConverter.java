package io.github.daniil547.board.label;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class LabelConverter extends DomainConverter<LabelDto, Label> {
    @Override
    protected Label transferEntitySpecificFieldsFromDto(LabelDto dto) {
        Label label = new Label();

        label.setBoardId(dto.getBoardId());
        label.setColor(dto.getColor());
        label.setName(dto.getName());

        return label;
    }

    @Override
    protected LabelDto transferDtoSpecificFieldsFromEntity(Label entity) {
        LabelDto dto = new LabelDto();

        dto.setBoardId(entity.getBoardId());
        dto.setColor(entity.getColor());
        dto.setName(entity.getName());

        return dto;
    }
}
