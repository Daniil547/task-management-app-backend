package io.github.daniil547.board;

import io.github.daniil547.board.label.LabelConverter;
import io.github.daniil547.common.services.PageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter extends PageConverter<BoardDto, Board> {
    private final LabelConverter labelConverter;

    @Autowired
    public BoardConverter(LabelConverter labelConverter) {
        this.labelConverter = labelConverter;
    }


    @Override
    protected Board transferEntitySpecificFieldsFromDto(BoardDto dto) {
        Board board = new Board();

        board.setWorkspaceId(dto.getWorkspaceId());
        board.setActive(dto.getActive());
        board.setVisibility(dto.getVisibility());
        board.setLabels(dto.getLabelDtos()
                           .stream()
                           .map(labelConverter::entityFromDto)
                           .toList());

        return board;
    }

    @Override
    protected BoardDto transferDtoSpecificFieldsFromEntity(Board entity) {
        BoardDto dto = new BoardDto();

        dto.setWorkspaceId(entity.getWorkspaceId());
        dto.setActive(entity.getActive());
        dto.setVisibility(entity.getVisibility());
        dto.setLabelDtos(entity.getLabels()
                               .stream()
                               .map(labelConverter::dtoFromEntity)
                               .toList());

        return dto;
    }
}