package io.github.daniil547.card;

import io.github.daniil547.board.label.LabelConverter;
import io.github.daniil547.card.elements.attachment.AttachmentConverter;
import io.github.daniil547.card.elements.check_list.CheckListConverter;
import io.github.daniil547.card.elements.reminder.Reminder;
import io.github.daniil547.card.elements.reminder.ReminderConverter;
import io.github.daniil547.card.elements.reminder.ReminderDto;
import io.github.daniil547.common.services.PageConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CardConverter extends PageConverter<CardDto, Card> {
    private final LabelConverter labelConverter;
    private final ReminderConverter reminderConverter;
    private final CheckListConverter checkListConverter;
    private final AttachmentConverter attachmentConverter;

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
        card.setAttachments(dto.getAttachmentDtos()
                               .stream()
                               .map(attachmentConverter::entityFromDto)
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
        dto.setAttachmentDtos(entity.getAttachments()
                                    .stream()
                                    .map(attachmentConverter::dtoFromEntity)
                                    .collect(Collectors.toList()));

        return dto;
    }
}