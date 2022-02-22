package io.github.daniil547.board;

import io.github.daniil547.board.label.Label;
import io.github.daniil547.common.domain.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "boards")
@AttributeOverride(name = "pageDescription",
                   column = @Column(length = 750))
public class Board extends Page {
    private UUID workspaceId;

    @Column(columnDefinition = "enum('PUBLIC', 'WORKSPACE', 'PRIVATE')")
    @Enumerated(EnumType.STRING)
    private BoardVisibility visibility = BoardVisibility.PRIVATE;

    @Column
    private Boolean active = true;

    @OneToMany(mappedBy = "boardId",
               fetch = FetchType.EAGER,
               cascade = CascadeType.ALL)
    private List<Label> labels = new ArrayList<>();
}
