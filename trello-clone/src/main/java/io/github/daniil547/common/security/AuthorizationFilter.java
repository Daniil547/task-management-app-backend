package io.github.daniil547.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final Map<String, AuthorizationChecker<?>> authorizationCheckers = new HashMap<>();
    private final RequestMatcher requestMatcher;


    @Autowired
    public AuthorizationFilter(List<AuthorizationChecker<?>> authorizationCheckers,
                               @Qualifier("whiteListedUrisMatcher") RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
        for (AuthorizationChecker<?> checker : authorizationCheckers) {
            this.authorizationCheckers.put(checker.getGuardedResourceName(), checker);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String endpoint = getEndpoint(request);

        AuthorizationChecker<?> authorizationChecker = authorizationCheckers.get(endpoint);

        if (authorizationChecker == null) { // endpoint doesn't require authentication
            filterChain.doFilter(request, response);
            return;
        }

        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID requestedResourceId = UUID.fromString(request.getParameter("id"));
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod()); // shouldn't fail as the request injected by Spring *should* contain a valid method

        if (authorizationChecker.isActionAuthorized(userId, requestedResourceId, httpMethod)) {
            filterChain.doFilter(request, response);
        } else {
            SecurityContextHolder.clearContext();

            throw new AccessDeniedException("User " + userId + "has no rights to perform " + httpMethod + " on /" + endpoint + "/" + requestedResourceId);
        }
    }


    private String getEndpoint(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        int indexOfSlash = requestURI.indexOf("/", 1);
        if (indexOfSlash == -1) {
            return requestURI.substring(1);
        } else {
            return requestURI.substring(1, indexOfSlash);
        }
    }
}
