package io.github.daniil547.card.elements;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PrintStreamReminderAction implements ReminderAction {
    PrintStream printStream;

    @Override
    public void execute(Reminder reminder) {
        printStream.println(reminder.getMessage());
    }
}
