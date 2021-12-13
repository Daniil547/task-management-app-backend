package io.github.daniil547.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Resource extends Domain {
    private Member createdBy;
    private ZonedDateTime createdWhen;
    private Member lastUpdatedBy;
    private ZonedDateTime lastUpdatedWhen;
}

