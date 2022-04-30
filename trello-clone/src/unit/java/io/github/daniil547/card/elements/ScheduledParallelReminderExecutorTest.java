package io.github.daniil547.card.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


class ParallelReminderExecutionTest {
    @Mock
    private ReminderExecutorUtilityService executorUtilityService;

    private final int parallelism = 5;

    private final int totalReminders = 1000;

    private final List<Reminder> rems = new ArrayList<>();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        for (int i = 0; i < totalReminders; i++) {
            Reminder reminder = new Reminder();
            reminder.setId(UUID.randomUUID());
            reminder.setMessage("test reminder #" + i);
            reminder.setAction(ReminderActionIdentifier.SEND_EMAIL);
            rems.add(reminder);
        }

        Mockito.doAnswer(invocation -> {
            Reminder arg = invocation.getArgument(0);
            System.out.println(arg.getMessage());
            return null;
        }).when(executorUtilityService).doExecuteReminder(ArgumentMatchers.any());

        Mockito.doAnswer(invocation -> null) // no-op
               .when(executorUtilityService)
               .deactivate(ArgumentMatchers.any());

        Mockito.when(executorUtilityService.doExecuteRemindersInParallel(rems, parallelism)).thenCallRealMethod();
    }

    @Test
    void executeActiveReminders() {
        executorUtilityService.doExecuteRemindersInParallel(rems, parallelism);

        Mockito.verify(executorUtilityService, Mockito.times(totalReminders))
               .doExecuteReminder(ArgumentMatchers.any());
    }
}