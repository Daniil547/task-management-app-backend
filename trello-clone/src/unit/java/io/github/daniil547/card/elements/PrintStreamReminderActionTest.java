package io.github.daniil547.card.elements;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintStream;

public class PrintStreamReminderActionTest {
    @Mock
    private PrintStream mockStream;
    @Mock
    private Reminder mockReminder;
    private final PrintStreamReminderAction reminderExecutor;

    public PrintStreamReminderActionTest() {
        MockitoAnnotations.openMocks(this);
        reminderExecutor = new PrintStreamReminderAction(mockStream);
    }

    @Test
    public void testReminderExecution() {
        Mockito.when(mockReminder.getMessage()).thenReturn("Lorem ipsum");
        reminderExecutor.execute(mockReminder);

        Mockito.verify(mockStream).println(mockReminder.getMessage());
    }


}