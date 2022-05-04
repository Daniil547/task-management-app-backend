package io.github.daniil547.card.elements.reminder;

public interface ReminderActionHandler {
    void execute(Reminder reminder);

    ReminderActionIdentifier getActionIdentifier();
}
