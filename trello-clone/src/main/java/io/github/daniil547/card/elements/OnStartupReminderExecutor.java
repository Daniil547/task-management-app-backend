package io.github.daniil547.card.elements;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class OnStartupReminderExecutor implements ApplicationListener<ContextRefreshedEvent> {
    ReminderExecutorUtilityService reminderExecutorUtilityService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ZonedDateTime now = ZonedDateTime.now();
        reminderExecutorUtilityService.doExecuteReminders(now);
    }
}
