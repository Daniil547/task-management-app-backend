package io.github.daniil547.user;

import io.github.daniil547.common.security.AuthorizationChecker;
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
public class JpaRelationsDefaultUserProfileAuthorizationChecker implements AuthorizationChecker<UserProfileDto> {
    private final Map<Triple<UUID, UUID, HttpMethod>, Boolean> cache = Collections.synchronizedMap(new LRUMap<>());

    @Override
    public boolean isActionAuthorized(UUID requesterUserId, UUID requestedUserId, HttpMethod action) {
        Triple<UUID, UUID, HttpMethod> args = Triple.of(requesterUserId, requestedUserId, action);
        if (cache.containsKey(args)) {
            return cache.get(args);
        }

        if (requesterUserId == null && requestedUserId == null) {
            boolean isPost = action.equals(HttpMethod.POST);

            cache.put(args, isPost);

            return isPost; // anonymous users are allowed to register
        }

        boolean res = switch (action) {
            case GET -> // any user can view any other user
                // might later change this by adding privacy preferences for users to configure
                    true;
            case POST -> false; // authenticated user aren't allowed to create new accounts
            case PUT, DELETE -> requesterUserId == requestedUserId; // user can update/delete only his own account
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
