package io.github.daniil547.workspace;

import io.github.daniil547.common.security.AuthorizationChecker;
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
public class JpaRelationsDefaultWorkspaceAuthorizationChecker implements AuthorizationChecker<WorkspaceDto> {
    private WorkspaceRepository workspaceRepository;
    private WorkspaceMemberRepository memberRepository;

    private final Map<Triple<UUID, UUID, HttpMethod>, Boolean> cache = Collections.synchronizedMap(new LRUMap<>(1000));

    @Override
    public boolean isActionAuthorized(UUID userId, UUID workspaceId, HttpMethod action) {
        Triple<UUID, UUID, HttpMethod> args = Triple.of(userId, workspaceId, action);
        if (cache.containsKey(args)) {
            return cache.get(args);
        }
        if (userId == null) {
            if (action.equals(HttpMethod.GET)) {
                if (workspaceId != null) {
                    WorkspaceVisibility visibility = workspaceRepository.getVisibilityById(workspaceId);
                    // anonymous users are allowed to view a public workspace
                    boolean isPublic = visibility.equals(WorkspaceVisibility.PUBLIC);
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
                if (workspaceId != null) {
                    WorkspaceVisibility visibility = workspaceRepository.getVisibilityById(workspaceId);
                    yield switch (visibility) {
                        case PUBLIC -> true; // everyone is allowed to view a public workspace
                        case PRIVATE -> // any member of a workspace is allowed to view the workspace
                                memberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId);
                    };
                } else { //true but requires more filtering down the line
                    yield true;
                }

            }
            case POST -> true; // any authenticated user is allowed to create a new workspace
            case PUT -> // let's say only admins and higher can edit workspaces
                    memberRepository.checkIsAMemberAndRoleAtLeast(userId, workspaceId, Role.ADMIN);
            case DELETE -> // let's say only owners can delete workspaces
                    memberRepository.checkIsAMemberAndRoleAtLeast(userId, workspaceId, Role.OWNER);
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
        return "workspaces";
    }
}
