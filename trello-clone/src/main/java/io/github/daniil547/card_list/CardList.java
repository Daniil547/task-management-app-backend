package io.github.daniil547.card_list;

import io.github.daniil547.common.domain.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "cardlists",
       uniqueConstraints = @UniqueConstraint(columnNames = {"board_id", "position"})
)
@AttributeOverride(name = "pageDescription",
                   column = @Column(length = 500))
public class CardList extends Page {
    /*@ManyToOne(targetEntity = Board.class)
    @JoinColumn(name = "board_id",
                referencedColumnName = "id")

     */
    @Column(name = "board_id")
    private UUID boardId;
    @Column
    private Integer position;
    @Column
    private Boolean active = true;
}
