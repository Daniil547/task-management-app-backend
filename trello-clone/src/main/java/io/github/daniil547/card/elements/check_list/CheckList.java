package io.github.daniil547.card.elements.check_list;

import io.github.daniil547.common.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "checklists")
public class CheckList extends Domain {
    @Column
    private String name;
    @Column
    private UUID cardId;
    @Column
    private Integer position;

    @OneToMany(fetch = FetchType.EAGER,
               mappedBy = "checkListId",
               cascade = CascadeType.ALL)
    private List<CheckableItem> items = new java.util.ArrayList<>();
}
