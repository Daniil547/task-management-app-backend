package io.github.daniil547.user;

import io.github.daniil547.common.rest_api_search.SearchQueryParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSearchQueryParser extends SearchQueryParser<User> {
    @Override
    public Specification<User> parse(String searchQuery) {
        String query = searchQuery.replaceAll("username", "pageName")
                                  .replaceAll("about", "pageDescription");

        return super.parse(query);
    }
}
