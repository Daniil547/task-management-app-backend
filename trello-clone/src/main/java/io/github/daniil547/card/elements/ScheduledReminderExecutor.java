package io.github.daniil547.card.elements;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;


@ConditionalOnProperty(prefix = "reminders",
                       name = "executors.scheduled.mode",
                       havingValue = "sequential",
                       matchIfMissing = false)
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduledReminderExecutor {
    private ReminderRepository reminderRepository;
    ReminderExecutorUtilityService reminderExecutorUtilityService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void executeActiveReminders() {
        // find rems that should go of during the next minute
        ZonedDateTime oneMinuteAhead = ZonedDateTime.now().plus(1, ChronoUnit.MINUTES);
        List<Reminder> dueReminders = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(oneMinuteAhead);
        reminderExecutorUtilityService.doExecuteReminders(dueReminders);
    }
}
