package io.github.daniil547.card.elements;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class ReminderConverter extends DomainConverter<ReminderDto, Reminder> {
    @Override
    protected Reminder transferEntitySpecificFieldsFromDto(ReminderDto dto) {
        Reminder reminder = new Reminder();

        reminder.setCardId(dto.getCardId());
        reminder.setRemindOn(dto.getRemindOn());
        reminder.setCompleted(dto.getCompleted());
        reminder.setEnd(dto.getEnd());

        return reminder;
    }

    @Override
    protected ReminderDto transferDtoSpecificFieldsFromEntity(Reminder entity) {
        ReminderDto dto = new ReminderDto();

        dto.setCardId(entity.getCardId());
        dto.setRemindOn(entity.getRemindOn());
        dto.setCompleted(entity.getCompleted());
        dto.setEnd(entity.getEnd().orElse(null));

        return dto;
    }
}
