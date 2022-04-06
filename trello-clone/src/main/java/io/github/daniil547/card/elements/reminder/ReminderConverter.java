package io.github.daniil547.card.elements.reminder;

import io.github.daniil547.common.services.DomainConverter;
import org.springframework.stereotype.Component;

@Component
public class ReminderConverter extends DomainConverter<ReminderDto, Reminder> {
    @Override
    protected Reminder transferEntitySpecificFieldsFromDto(ReminderDto dto) {
        Reminder reminder = new Reminder();

        reminder.setMessage(dto.getMessage());
        reminder.setStartOrDue(dto.getStartOrDue());
        reminder.setRemindOn(dto.getRemindOn());
        reminder.setCompleted(dto.getCompleted());
        reminder.setEnd(dto.getEnd());

        return reminder;
    }

    @Override
    protected ReminderDto transferDtoSpecificFieldsFromEntity(Reminder entity) {
        ReminderDto dto = new ReminderDto();

        dto.setMessage(entity.getMessage());
        dto.setStartOrDue(entity.getStartOrDue());
        dto.setRemindOn(entity.getRemindOn());
        dto.setCompleted(entity.getCompleted());
        dto.setEnd(entity.getEnd().orElse(null));

        return dto;
    }
}
