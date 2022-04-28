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
@Table(name = "board_members",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"board_id",
                                                             "user_id"})}
)
public class BoardMember extends Member {
    @Column(name = "board_id")
    private UUID boardId;

    @Override
    protected UUID getPlace() {
        return this.getBoardId();
    }
}
