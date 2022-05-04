package io.github.daniil547.user.member;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "workspace_members",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"workspace_id",
                                                             "user_id"})}
)
public class WorkspaceMember extends Member {
    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Override
    protected UUID getPlaceId() {
        return this.getWorkspaceId();
    }
}
