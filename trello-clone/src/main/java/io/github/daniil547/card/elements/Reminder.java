package io.github.daniil547.card.elements;

import io.github.daniil547.card.Card;
import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

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

    @OneToOne(targetEntity = Card.class,
              optional = true,
              fetch = FetchType.EAGER,
              orphanRemoval = false)
    @JoinColumn(name = "card_id",
                referencedColumnName = "id")
    private UUID cardId;

    public Optional<ZonedDateTime> getEnd() {
        return Optional.ofNullable(end);
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
