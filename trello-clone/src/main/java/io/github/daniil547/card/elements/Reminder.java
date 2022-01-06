package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Reminder extends Domain {
    private ZonedDateTime startOrDue;
    private Optional<ZonedDateTime> end;
    private ZonedDateTime remindOn;
    private Boolean completed = false;

    /*TODO: other methods*/
}
