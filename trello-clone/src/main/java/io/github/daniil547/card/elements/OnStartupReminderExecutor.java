package io.github.daniil547.card.elements;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class OnStartupReminderExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private final ReminderRepository reminderRepository;
    private final ReminderAction reminderAction;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ZonedDateTime now = ZonedDateTime.now();
        List<Reminder> remsToExecute = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(now);
        remsToExecute.forEach(reminderAction::execute);
        reminderRepository.deactivateAllExecuted(now);
    }
}
