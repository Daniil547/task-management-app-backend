package io.github.daniil547.card.elements;

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
@Table(name = "checkable_items")
public class CheckableItem extends Domain {
    @Column
    private Boolean checked = false;
    @Column
    private String description;

    @Column(name = "checklist_id")
    private UUID checkListId;
    @Column
    private Short position;
}
