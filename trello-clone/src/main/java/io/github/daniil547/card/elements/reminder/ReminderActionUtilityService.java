package io.github.daniil547.card.elements.reminder;

import io.github.daniil547.card.Card;
import io.github.daniil547.card.CardRepository;
import io.github.daniil547.user.UserCredentials;
import io.github.daniil547.user.UserCredentialsRepository;
import io.github.daniil547.user.member.BoardMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReminderActionUtilityService {
    private CardRepository cardRepository;
    private UserCredentialsRepository userRepository;
    private BoardMemberRepository boardMemberRepository;

    List<String> findEmailsOfPeopleToSendReminder(UUID reminderId) {
        Card card = cardRepository.findByReminderId(reminderId)
                                  .orElseThrow(() -> {
                                      throw new IllegalStateException("Reminder" + reminderId + " exists but isn't attached to any card");
                                  });

        List<UUID> usersIdsByMembersIds = boardMemberRepository.findUsersIdsByMembersIds(card.getAssignedMembers().stream().toList());

        return userRepository.findAllById(usersIdsByMembersIds)
                             .stream()
                             .map(UserCredentials::getEmail)
                             .toList();
    }
}
