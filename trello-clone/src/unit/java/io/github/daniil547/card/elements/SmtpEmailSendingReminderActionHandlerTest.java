package io.github.daniil547.card.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SmtpEmailSendingReminderActionHandlerTest {

    @Value("${spring.mail.reminder-sender-address}")
    private String from;

    @Mock
    private ReminderActionUtilityService reminderExecutorUtilityService;
    private final Reminder reminder = new Reminder();
    @Mock
    private JavaMailSender mailSender;

    private SmtpEmailSendingReminderActionHandler emailHandler;
    private final List<String> emails = List.of("testemail1@example.org", "testemail2@example.org", "testemail3@example.org");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailHandler = new SmtpEmailSendingReminderActionHandler(from, reminderExecutorUtilityService, mailSender);


        reminder.setId(UUID.randomUUID());
        reminder.setMessage("Reminder email message something-something");

        Mockito.when(reminderExecutorUtilityService.findEmailsOfPeopleToSendReminder(reminder.getId()))
               .thenReturn(emails);
    }

    @Test
    void execute() {
        emailHandler.execute(reminder);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emails.toArray(new String[0]));
        message.setSubject("You have a notification about a task");
        message.setText(reminder.getMessage());

        Mockito.verify(mailSender).send(message);
    }
}