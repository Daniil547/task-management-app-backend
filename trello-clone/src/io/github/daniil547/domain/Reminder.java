package io.github.daniil547.domain;

import java.time.ZonedDateTime;
import java.util.Optional;

public class Reminder {
    private ZonedDateTime startOrDue;
    private Optional<ZonedDateTime> end;
    private ZonedDateTime remindOn;
    private Boolean completed = false;

    /*TODO: accessors*/
    /*TODO: constructors*/
    /*TODO: other methods*/
}
