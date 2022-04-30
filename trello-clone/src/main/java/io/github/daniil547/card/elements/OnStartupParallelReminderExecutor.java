package io.github.daniil547.card.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Primary
@Component
public class OnStartupParallelReminderExecutor implements ApplicationListener<ContextRefreshedEvent> {
    private final ReminderRepository reminderRepository;
    private final ReminderExecutorUtilityService executorUtilityService;
    private final Integer parallelism;

    @Autowired
    public OnStartupParallelReminderExecutor(ReminderRepository reminderRepository,
                                             ReminderExecutorUtilityService executorUtilityService,
                                             @Value("${reminders.execution.parallelism}") Integer parallelism) {
        this.reminderRepository = reminderRepository;
        this.executorUtilityService = executorUtilityService;
        this.parallelism = parallelism;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ZonedDateTime now = ZonedDateTime.now();
        List<Reminder> dueReminders = reminderRepository.findAllByGoneOffFalseAndRemindOnBefore(now);
        executorUtilityService.doExecuteRemindersInParallel(dueReminders, parallelism);
    }
}