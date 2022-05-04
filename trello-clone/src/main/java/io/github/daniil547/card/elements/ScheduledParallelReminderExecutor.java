package io.github.daniil547.card.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(prefix = "reminders",
                       name = "executors.scheduled.mode",
                       havingValue = "parallel",
                       matchIfMissing = true)
@Component
public class ScheduledParallelReminderExecutor {
    private final ReminderRepository reminderRepository;
    private final ReminderExecutorUtilityService executorUtilityService;
    private final Integer parallelism;

    @Autowired
    public ScheduledParallelReminderExecutor(ReminderRepository reminderRepository,
                                             ReminderExecutorUtilityService executorUtilityService,
                                             @Value("${reminders.execution.parallelism}") Integer parallelism) {
        this.reminderRepository = reminderRepository;
        this.executorUtilityService = executorUtilityService;
        this.parallelism = parallelism;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void executeActiveReminders() {
        // find rems that should go of during the next minute
        ZonedDateTime oneMinuteAhead = ZonedDateTime.now().plus(1, ChronoUnit.MINUTES);
        List<Reminder> dueReminders = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(oneMinuteAhead);

        executorUtilityService.doExecuteRemindersInParallel(dueReminders, parallelism);
    }
}
