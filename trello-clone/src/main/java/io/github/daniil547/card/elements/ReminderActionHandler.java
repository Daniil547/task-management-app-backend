package io.github.daniil547.card.elements;

public interface ReminderActionHandler {
    void execute(Reminder reminder);

    ReminderActionIdentifier getActionIdentifier();
}
