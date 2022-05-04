package io.github.daniil547;

import io.github.daniil547.card.elements.reminder.ReminderActionHandler;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {
    @Primary
    @Bean
    public ReminderActionHandler mockReminderExecutor() {
        return Mockito.mock(ReminderActionHandler.class);
    }
}
