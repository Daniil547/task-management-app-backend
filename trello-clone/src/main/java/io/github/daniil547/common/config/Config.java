package io.github.daniil547.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.daniil547.card.elements.ReminderActionHandler;
import io.github.daniil547.card.elements.ReminderActionIdentifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("io.github.daniil547")
@EnableScheduling
public class Config {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = JsonMapper.builder()
                                              .addModule(new JavaTimeModule())
                                              .build();
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

        return objectMapper;
    }

    @Bean
    public Map<ReminderActionIdentifier, ReminderActionHandler> reminderActionHandlerRegistry(List<ReminderActionHandler> actions) {
        final String errMessageBase = "reminderActionHandlerRegistry bean received two ReminderActionHandler with the same ReminderActionIdentifier:";

        EnumMap<ReminderActionIdentifier, ReminderActionHandler> registry = new EnumMap<>(ReminderActionIdentifier.class);
        // if Map.put() is given a duplicate K it just replaces previous V
        // but in this case a duplicate K (ReminderActionIdentifier) would indicate a mistake in the code or config
        actions.forEach(action -> {
            if (registry.containsKey(action.getActionIdentifier())) {
                throw new AssertionError(errMessageBase + action.getActionIdentifier().toString());
            } else {
                registry.put(action.getActionIdentifier(), action);
            }
        });
        return registry;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
