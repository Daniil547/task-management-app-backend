package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReminderExecutorUtilityService {
    private ReminderRepository reminderRepository;
    private Map<ReminderActionIdentifier, ReminderActionHandler> actionHandlerRegistry;

    void doExecuteReminders(ZonedDateTime due) {
        List<Reminder> dueReminders = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(due);
        List<UUID> executedReminders = dueReminders.stream()
                                                   .peek(rem -> actionHandlerRegistry
                                                           .get(rem.getAction())
                                                           .execute(rem))
                                                   .map(Domain::getId)
                                                   .toList();

        if (!executedReminders.isEmpty()) {
            reminderRepository.deactivateAll(executedReminders);
        }
    }
}
