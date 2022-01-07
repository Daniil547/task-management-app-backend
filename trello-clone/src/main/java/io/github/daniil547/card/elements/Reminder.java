package io.github.daniil547.card.elements;

import io.github.daniil547.common.domain.Domain;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Reminder extends Domain {
    private ZonedDateTime startOrDue;
    private Optional<ZonedDateTime> end;
    private ZonedDateTime remindOn;
    private Boolean completed = false;
}
