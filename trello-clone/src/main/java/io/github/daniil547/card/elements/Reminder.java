package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "reminders")
public class Reminder extends Domain {
    @Column
    private ZonedDateTime startOrDue;
    @Column
    private ZonedDateTime end;
    @Column
    private ZonedDateTime remindOn;
    @Column
    private Boolean completed = false;
    @Column
    private String message;
    @Column
    private Boolean goneOff = false;

    public Optional<ZonedDateTime> getEnd() {
        return Optional.ofNullable(end);
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
