package io.github.daniil547.card;

import io.github.daniil547.board.label.Label;
import io.github.daniil547.card.elements.CheckList;
import io.github.daniil547.card.elements.Reminder;
import io.github.daniil547.common.domain.Page;
import io.github.daniil547.user.member.BoardMember;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//TODO fix MultipleBagFetchException properly
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Entity
@Table(name = "cards",
       uniqueConstraints = @UniqueConstraint(columnNames = {"cardlist_id", "position"})
)
@AttributeOverride(name = "pageDescription",
                   column = @Column(length = 350))
public class Card extends Page {
    @Column
    private Boolean active = true;
    @Column(name = "cardlist_id")
    private UUID cardListId;
    @Column
    private Integer position;

    @ManyToMany(targetEntity = Label.class,
                fetch = FetchType.EAGER)
    @JoinTable(name = "labels_attached_to_cards",
               joinColumns = {@JoinColumn(name = "card_id")},
               inverseJoinColumns = {@JoinColumn(name = "label_id")},
               uniqueConstraints = {@UniqueConstraint(columnNames = {"card_id",
                                                                     "label_id"})})
    // a workaround for MultipleBagFetchException
    private Set<Label> attachedLabels = new HashSet<>();

    @ManyToMany(targetEntity = BoardMember.class,
                fetch = FetchType.EAGER)
    @JoinTable(name = "members_assigned_to_cards",
               joinColumns = {@JoinColumn(name = "card_id")},
               inverseJoinColumns = {@JoinColumn(name = "member_id")},
               uniqueConstraints = {@UniqueConstraint(columnNames = {"card_id",
                                                                     "member_id"})})
    // a workaround for MultipleBagFetchException
    private Set<UUID> assignedMembers = new HashSet<>();

    @OneToMany(mappedBy = "cardId",
               fetch = FetchType.EAGER,
               cascade = CascadeType.ALL)
    // a workaround for MultipleBagFetchException
    private Set<CheckList> checkLists = new HashSet<>();

    @OneToOne(targetEntity = Reminder.class,
              optional = true,
              fetch = FetchType.EAGER,
              orphanRemoval = true,
              mappedBy = "cardId",
              cascade = CascadeType.ALL)
    private Reminder reminder;
}
