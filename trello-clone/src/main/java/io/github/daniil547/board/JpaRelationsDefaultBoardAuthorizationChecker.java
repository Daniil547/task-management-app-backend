package io.github.daniil547.board;

import io.github.daniil547.common.security.AuthorizationChecker;
import io.github.daniil547.user.member.BoardMemberRepository;
import io.github.daniil547.user.member.Role;
import io.github.daniil547.user.member.WorkspaceMemberRepository;
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
public class JpaRelationsDefaultBoardAuthorizationChecker implements AuthorizationChecker<BoardDto> {
    private BoardRepository boardRepository;
    private BoardMemberRepository boardMemberRepository;
    private WorkspaceMemberRepository workspaceMemberRepository;
    private final Map<Triple<UUID, UUID, HttpMethod>, Boolean> cache = Collections.synchronizedMap(new LRUMap<>());

    @Override
    public boolean isActionAuthorized(UUID userId, UUID boardId, HttpMethod action) {
        Triple<UUID, UUID, HttpMethod> args = Triple.of(userId, boardId, action);
        if (cache.containsKey(args)) {
            return cache.get(args);
        }

        if (userId == null) {
            if (action.equals(HttpMethod.GET)) {
                if (boardId != null) {
                    BoardVisibility visibility = boardRepository.getVisibilityById(boardId);
                    // anonymous users are allowed to view a public board
                    boolean isPublic = visibility.equals(BoardVisibility.PUBLIC);
                    cache.put(args, isPublic);
                    return isPublic;
                } else { //true but requires more filtering down the line
                    cache.put(args, true);
                    return true;
                }
            } else {
                cache.put(args, false);
                return false;
            }
        }

        boolean res = switch (action) {
            case GET -> {
                if (boardId != null) {
                    BoardVisibility visibility = boardRepository.getVisibilityById(boardId);
                    yield switch (visibility) {
                        case PUBLIC -> true;// everyone is allowed to view a public board
                        case WORKSPACE -> // available to any member of the enclosing workspace
                                workspaceMemberRepository.existsByUserIdAndWorkspaceId(userId, boardRepository.getWorkspaceIdById(boardId));
                        case PRIVATE -> // available only to members of the particular board
                                boardMemberRepository.existsByUserIdAndBoardId(userId, boardId);
                    };
                } else { //true but requires more filtering down the line
                    yield true;
                }
            }
            case POST -> // workspace admins and higher can add new boards to their workspaces
                    workspaceMemberRepository.checkIsAMemberAndRoleAtLeast(userId, boardId, Role.ADMIN);
            case PUT -> // board admins and higher can edit boards
                    boardMemberRepository.checkIsAMemberAndRoleAtLeast(userId, boardId, Role.ADMIN);
            case DELETE -> // workspace admins (and higher) and board owners can delete boards
                    boardMemberRepository.checkIsAMemberAndRoleAtLeast(userId, boardId, Role.OWNER)
                    || workspaceMemberRepository.checkIsAMemberAndRoleAtLeast(userId, boardId, Role.ADMIN);
            default -> {
                handleUnsupportedHttpMethod(action);
                yield false; // currently unreachable
            }
        };

        cache.put(args, res);

        return res;
    }

    @Override
    public String getGuardedResourceName() {
        return "boards";
    }
}
