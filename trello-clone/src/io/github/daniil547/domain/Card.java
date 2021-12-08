package io.github.daniil547.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Card extends NestedPage
                  implements Copyable<Card>,
                             PartiallyCopyable<Card> {
    private Member creator;
    private boolean active;

    private List<MonoCardElement> monoCardElements;
    private List<ListableCardElement> listableCardElements;
    private List<Label> attachedLabels;
    private Set<Member> assignedMembers;
    private Optional<Reminder> reminder;

    /*TODO: accessors*/
    /*TODO: constructors*/
    /*TODO: other methods*/
    /*TODO: implement*/

}
