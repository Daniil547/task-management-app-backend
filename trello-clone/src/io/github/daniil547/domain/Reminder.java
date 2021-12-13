package io.github.daniil547.domain;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Optional;

@Data
public class Reminder extends Domain {
    private ZonedDateTime startOrDue;
    private Optional<ZonedDateTime> end;
    private ZonedDateTime remindOn;
    private Boolean completed = false;

    /*TODO: other methods*/
}
