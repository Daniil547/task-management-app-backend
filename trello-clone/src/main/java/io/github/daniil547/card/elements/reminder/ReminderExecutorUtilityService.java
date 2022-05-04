package io.github.daniil547.card.elements.reminder;

import io.github.daniil547.common.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReminderExecutorUtilityService {
    private ReminderRepository reminderRepository;
    private Map<ReminderActionIdentifier, ReminderActionHandler> actionHandlerRegistry;

    void doExecuteReminders(List<Reminder> reminders) {
        List<UUID> executedReminders = reminders.stream()
                                                .peek(this::doExecuteReminder)
                                                .map(Domain::getId)
                                                .toList();

        log.info("{} reminders were executed sequentially (of {}); there were {} errors",
                 executedReminders.size(), reminders.size(), executedReminders.size() - reminders.size());

        deactivate(executedReminders);
    }

    int doExecuteRemindersInParallel(List<Reminder> reminders, int parallelism) {
        AtomicInteger executedRemCount = new AtomicInteger(0);

        ExecutorService executorService = Executors.newWorkStealingPool(parallelism); // uses fork/join under the hood

        Map<UUID, Future<?>> tasks = reminders.stream() // map a reminder to a callable, which executes it and returns its ID
                                              .map(rem -> Pair.<UUID, Runnable>of( // compiler isn't able infer the type of () -> {...}
                                                                                   rem.getId(),
                                                                                   () -> {
                                                                                       this.doExecuteReminder(rem);
                                                                                       executedRemCount.incrementAndGet();
                                                                                   })
                                              )
                                              .collect(Collectors.toMap(
                                                      Pair::getFirst,
                                                      pair -> executorService.submit(pair.getSecond()))
                                              );

        List<UUID> remsToDeactivate = new ArrayList<>(reminders.size());

        for (Map.Entry<UUID, Future<?>> task : tasks.entrySet()) {
            try {
                task.getValue().get();
                remsToDeactivate.add(task.getKey());
            } catch (InterruptedException e) {
                // logging is expected to be at least WARN at all times, so concatenation should be ok
                log.error("Failed to execute reminder " + task.getKey(), e);
                throw new RuntimeException("Failed to execute reminder" + task.getKey() + ": thread was interrupted", e);
            } catch (ExecutionException e) { // same here
                log.error("Failed to execute reminder " + task.getKey(), e);
                throw new RuntimeException("Failed to execute reminder" + task.getKey() + ": execution resulted in exception", e);
            }
        }

        int unexecutedAmount = reminders.size() - remsToDeactivate.size();

        log.info("{} reminders were executed in parallel (of {}); there were {} errors",
                 remsToDeactivate.size(), reminders.size(), unexecutedAmount);

        if (unexecutedAmount > 0) {
            log.warn("{} reminders weren't sent", unexecutedAmount);
        }

        deactivate(remsToDeactivate);

        return remsToDeactivate.size();
    }

    public void doExecuteReminder(Reminder rem) {
        actionHandlerRegistry
                .get(rem.getAction())
                .execute(rem);
    }

    public void deactivate(List<UUID> executedReminders) {
        if (!executedReminders.isEmpty()) {
            reminderRepository.deactivateAll(executedReminders);
        }
    }
}
