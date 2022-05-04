package io.github.daniil547.card_list;

import io.github.daniil547.board.BoardRepository;
import io.github.daniil547.board.JpaRelationsDefaultBoardAuthorizationChecker;
import io.github.daniil547.common.security.AuthorizationChecker;
import io.github.daniil547.user.member.BoardMemberRepository;
import io.github.daniil547.user.member.Role;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JpaRelationsDefaultCardListAuthorizationChecker implements AuthorizationChecker<CardListDto> {
    private CardListRepository cardListRepository;
    private BoardRepository boardRepository;
    private BoardMemberRepository boardMemberRepository;
    private JpaRelationsDefaultBoardAuthorizationChecker boardAuthorizationChecker;
    private final Map<Triple<UUID, UUID, HttpMethod>, Boolean> cache = Collections.synchronizedMap(new LRUMap<>());

    @Override
    public boolean isActionAuthorized(UUID userId, UUID cardListId, HttpMethod action) {
        Triple<UUID, UUID, HttpMethod> args = Triple.of(userId, cardListId, action);
        if (cache.containsKey(args)) {
            return cache.get(args);
        }

        if (cardListId != null) {
            UUID boardId = cardListRepository.getBoardIdById(cardListId);


            boolean res = switch (action) {
                case GET ->
                        boardAuthorizationChecker.isActionAuthorized(userId, boardId, action); // if a user can view the board he can view its contents
                case POST, PUT, DELETE ->
                        boardMemberRepository.checkIsAMemberAndRoleAtLeast(userId, boardId, Role.MEMBER);
                default -> {
                    handleUnsupportedHttpMethod(action);
                    yield false;
                }
            };
            cache.put(args, res);

            return res;
        } else { // more filtering is needed here too
            cache.put(args, true);

            return true;
        }


    }

    @Override
    public String getGuardedResourceName() {
        return "card-lists";
    }
}
