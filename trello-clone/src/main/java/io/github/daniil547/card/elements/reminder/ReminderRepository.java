package io.github.daniil547.card.elements.reminder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Component
public interface ReminderRepository extends JpaRepository<Reminder, UUID> {
    @Query("update Reminder r set r.goneOff = true where r.id in ?1")
    @Modifying
    @Transactional
    void deactivateAll(Iterable<UUID> reminders);

    List<Reminder> findAllByGoneOffFalseAndRemindOnBefore(ZonedDateTime beforeThis);
}
