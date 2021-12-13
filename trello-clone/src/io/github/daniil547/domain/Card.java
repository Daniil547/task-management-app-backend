package io.github.daniil547.domain;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
public class Card extends NestedPage
        implements Copyable<Card>,
        PartiallyCopyable<Card> {
    private Member creator;
    private Boolean active = true;

    private List<MonoCardElement> monoCardElements;
    private List<ListableCardElement> listableCardElements;
    private List<Label> attachedLabels;
    private Set<Member> assignedMembers;
    private Optional<Reminder> reminder;

    /*TODO: other methods*/
    /*TODO: implement*/
}
