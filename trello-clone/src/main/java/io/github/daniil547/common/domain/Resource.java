package io.github.daniil547.common.domain;

import io.github.daniil547.user.member.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Resource extends Domain {
    private String title;
    private String name;
    private String description;
    private Member createdBy;
    private ZonedDateTime createdWhen;
    private Member lastUpdatedBy;
    private ZonedDateTime lastUpdatedWhen;
}
