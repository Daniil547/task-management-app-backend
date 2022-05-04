package io.github.daniil547.card.elements.reminder;

import io.github.daniil547.card.CardDto;
import io.github.daniil547.common.exceptions.CustomValidationFailureException;
import io.github.daniil547.common.validation.CustomValidator;
import org.springframework.stereotype.Component;

@Component
public class StartBeforeEndReminderValidator implements CustomValidator<CardDto> { //Reminder has no dedicated service, so CustomValidator<ReminderDto> wouldn't be injected anywhere
    @Override
    public void validate(CardDto dto) {
        ReminderDto reminderDto = dto.getReminderDto();
        if (reminderDto != null) {
            if (reminderDto.getEnd() != null) {
                if (reminderDto.getStartOrDue().isAfter(reminderDto.getEnd())) {
                    throw new CustomValidationFailureException("end date must be after start date", "endDate", reminderDto.getEnd().toString());
                }
            }
        }
    }
}
