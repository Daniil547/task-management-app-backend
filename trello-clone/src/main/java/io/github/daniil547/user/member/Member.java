package io.github.daniil547.user.member;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@MappedSuperclass
@AttributeOverrides({@AttributeOverride(name = "id",
                                        column = @Column(name = "member_id",
                                                         nullable = false))})
public abstract class Member extends Domain {
    @Column(name = "user_id")
    private UUID userId;
    @Column(columnDefinition = "enum('OWNER', 'ADMIN', 'MEMBER', 'GUEST')")
    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;

    protected abstract UUID getPlaceId();
}
