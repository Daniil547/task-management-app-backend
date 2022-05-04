package io.github.daniil547.card;

import io.github.daniil547.card_list.JpaRelationsDefaultCardListAuthorizationChecker;
import io.github.daniil547.common.security.AuthorizationChecker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JpaRelationsDefaultCardAuthorizationChecker implements AuthorizationChecker<CardDto> {
    private CardRepository cardRepository;
    private JpaRelationsDefaultCardListAuthorizationChecker cardListAuthorizationChecker;

    @Override
    public boolean isActionAuthorized(UUID userId, UUID cardId, HttpMethod action) {
        // if a user has rights to operate on the enclosing cardList, then he can operate on its cards
        // i.e. access rules for cards mirror those of cardLists
        if (cardId != null) {
            return cardListAuthorizationChecker.isActionAuthorized(userId, cardRepository.getCardListIdById(cardId), action);
        } else {// even more filtering
            // that's quite a bad split of one concern across classes :(
            return true;
        }
    }

    @Override
    public String getGuardedResourceName() {
        return "cards";
    }
}
