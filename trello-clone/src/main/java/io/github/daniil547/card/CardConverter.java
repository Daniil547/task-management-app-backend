package io.github.daniil547.card;

import io.github.daniil547.board.label.LabelConverter;
import io.github.daniil547.card.elements.CheckListConverter;
import io.github.daniil547.card.elements.Reminder;
import io.github.daniil547.card.elements.ReminderConverter;
import io.github.daniil547.card.elements.ReminderDto;
import io.github.daniil547.common.services.PageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class CardConverter extends PageConverter<CardDto, Card> {
    private final LabelConverter labelConverter;
    private final ReminderConverter reminderConverter;
    private final CheckListConverter checkListConverter;

    @Autowired
    public CardConverter(LabelConverter labelConverter,
                         ReminderConverter reminderConverter,
                         CheckListConverter checkListConverter) {
        this.labelConverter = labelConverter;
        this.reminderConverter = reminderConverter;
        this.checkListConverter = checkListConverter;
    }

    @Override
    protected Card transferEntitySpecificFieldsFromDto(CardDto dto) {
        Card card = new Card();

        card.setActive(dto.getActive());
        card.setCardListId(dto.getCardListId());
        card.setPosition(dto.getPosition());
        ReminderDto reminderDto = dto.getReminderDto();
        Reminder reminder;
        if (reminderDto == null) {
            reminder = null;
        } else {
            reminder = reminderConverter.entityFromDto(reminderDto);
        }
        ;
        card.setReminder(reminder);
        card.setAssignedMembers(new HashSet<>(dto.getAssignedMembers())); //IDs only
        card.setAttachedLabels(dto.getAttachedLabelDtos()
                                  .stream()
                                  .map(labelConverter::entityFromDto)
                                  .collect(Collectors.toSet()));
        card.setCheckLists(dto.getCheckListDtos()
                              .stream()
                              .map(checkListConverter::entityFromDto)
                              .collect(Collectors.toSet()));

        return card;
    }

    @Override
    protected CardDto transferDtoSpecificFieldsFromEntity(Card entity) {
        CardDto dto = new CardDto();

        dto.setActive(entity.getActive());
        dto.setCardListId(entity.getCardListId());
        dto.setPosition(entity.getPosition());
        Reminder reminder = entity.getReminder();
        ReminderDto reminderDto;
        if (reminder == null) {
            reminderDto = null;
        } else {
            reminderDto = reminderConverter.dtoFromEntity(reminder);
        }
        ;
        dto.setReminderDto(reminderDto);
        dto.setAssignedMembers(dto.getAssignedMembers()); //IDs only
        dto.setAttachedLabelDtos(entity.getAttachedLabels()
                                       .stream()
                                       .map(labelConverter::dtoFromEntity)
                                       .toList());
        dto.setCheckListDtos(entity.getCheckLists()
                                   .stream()
                                   .map(checkListConverter::dtoFromEntity)
                                   .toList());
        return dto;
    }
}