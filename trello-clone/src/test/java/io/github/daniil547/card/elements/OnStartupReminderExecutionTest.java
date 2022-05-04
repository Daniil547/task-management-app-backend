package io.github.daniil547.card.elements;

import io.github.daniil547.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Main.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class OnStartupReminderExecutionTest {
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private ReminderActionHandler reminderActionHandler;
    @Autowired
    private ReminderConverter reminderConverter;
    @Autowired
    private OnStartupReminderExecutor reminderExecutor;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(reminderActionHandler);
        Mockito.clearInvocations(reminderActionHandler);
    }

    @Test
    public void testReminderExecution() throws Exception {

        ReminderDto reminderDto = new ReminderDto();
        reminderDto.setMessage("Don't forget to test Reminder! Oh, wait...");
        reminderDto.setStartOrDue(ZonedDateTime.now().plus(2, ChronoUnit.HOURS));
        reminderDto.setRemindOn(ZonedDateTime.now().minus(1, ChronoUnit.MINUTES));

        System.out.println(reminderDto.getRemindOn());

        Reminder reminder = reminderConverter.entityFromDto(reminderDto);
        reminder = reminderRepository.save(reminder);

        reminderExecutor.onApplicationEvent(null); //event object isn't actually used in the method

        Mockito.verify(reminderActionHandler).execute(ArgumentMatchers.any());

        assertTrue(reminderRepository.findById(reminder.getId()).get().getGoneOff());
    }
}
