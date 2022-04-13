package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduledReminderExecutor {
    private final ReminderRepository reminderRepository;
    private final ReminderAction reminderAction;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void executeActiveReminders() {
        // find rems that should go of during the next minute
        ZonedDateTime oneMinuteAhead = ZonedDateTime.now().plus(1, ChronoUnit.MINUTES);
        List<Reminder> dueReminders = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(oneMinuteAhead);
        List<UUID> executedReminders = dueReminders.stream()
                                                   .peek(reminderAction::execute)
                                                   .map(Domain::getId)
                                                   .toList();
        if (!executedReminders.isEmpty()) {
            reminderRepository.deactivateAll(executedReminders);
        }
    }
}
