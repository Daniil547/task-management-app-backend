package io.github.daniil547.card;

import io.github.daniil547.board.label.Label;
import io.github.daniil547.card.elements.Reminder;
import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.domain.interfaces.ListableCardElement;
import io.github.daniil547.common.domain.interfaces.MonoCardElement;
import io.github.daniil547.user.member.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Card extends Page {
    private Boolean active = true;

    private UUID cardListId;
    private Integer position;

    private List<MonoCardElement> monoCardElements = new ArrayList<>();
    private List<ListableCardElement> listableCardElements = new ArrayList<>();
    private List<Label> attachedLabels = new ArrayList<>();
    private Set<Member> assignedMembers = new HashSet<>();
    private Optional<Reminder> reminder = Optional.empty();
}
