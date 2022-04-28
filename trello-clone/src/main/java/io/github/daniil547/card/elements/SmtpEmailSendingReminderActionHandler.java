package io.github.daniil547.card.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SmtpEmailSendingReminderActionHandler implements ReminderActionHandler {

    private final String from;

    ReminderActionUtilityService remActionUtilService;
    JavaMailSender mailSender;

    @Autowired
    public SmtpEmailSendingReminderActionHandler(@Value("${reminders.actions.smtp.sender-address}") String from,
                                                 ReminderActionUtilityService remActionUtilService,
                                                 JavaMailSender mailSender) {
        this.from = from;
        this.remActionUtilService = remActionUtilService;
        this.mailSender = mailSender;
    }


    @Override
    public void execute(Reminder reminder) {
        UUID reminderId = reminder.getId();

        List<String> emails = remActionUtilService.findEmailsOfPeopleToSendReminder(reminderId);

        if (emails.size() == 0) return; // no members assigned -> no one receives email

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emails.toArray(new String[0]));
        message.setSubject("You have a notification about a task");
        message.setText(reminder.getMessage());
        mailSender.send(message);
    }

    public ReminderActionIdentifier getActionIdentifier() {
        return ReminderActionIdentifier.SEND_EMAIL;
    }
}
