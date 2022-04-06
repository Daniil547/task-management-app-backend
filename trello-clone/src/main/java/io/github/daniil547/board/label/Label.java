package io.github.daniil547.board.label;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "labels")
public class Label extends Domain {

    @Column
    private UUID boardId;

    @Column
    private String name;
    @Column
    private int color;
}
