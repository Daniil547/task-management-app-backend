package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class OnStartupReminderExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private final ReminderRepository reminderRepository;
    private final ReminderAction reminderAction;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ZonedDateTime now = ZonedDateTime.now();
        List<Reminder> dueReminders = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(now);
        List<UUID> executedReminders = dueReminders.stream()
                                                   .peek(reminderAction::execute)
                                                   .map(Domain::getId)
                                                   .toList();
        if (!executedReminders.isEmpty()) {
            reminderRepository.deactivateAll(executedReminders);
        }
    }
}
