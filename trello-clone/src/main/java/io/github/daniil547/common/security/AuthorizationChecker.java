package io.github.daniil547.common.security;

import io.github.daniil547.common.dto.DomainDto;
import org.springframework.http.HttpMethod;

import java.util.UUID;

/**
 * Represents a mechanism responsible for checking that access to a resource is authorized
 *
 * @param <R> type of the resource DTO protected by this checker
 */
public interface AuthorizationChecker<R extends DomainDto> {

    /**
     * Checks that a particular user has rights to perform given action on the given resource.
     *
     * @param userId     ID of a user whose access rights are to be checked; <code>null</code> signifies anonymous user
     * @param resourceId ID of a resource, access to which is to be authorized
     * @param action     what kind action the user wants to perform
     * @return whether the user has access to the resource
     */
    boolean isActionAuthorized(UUID userId, UUID resourceId, HttpMethod action);

    /**
     * @return name of the resource (== endpoint) protected by this checker
     */
    String getGuardedResourceName();

    default void handleUnsupportedHttpMethod(HttpMethod method) {
        throw new UnsupportedOperationException("HTTP method " + method.name() + "is not supported for " + getGuardedResourceName() + " resource");
    }
}
