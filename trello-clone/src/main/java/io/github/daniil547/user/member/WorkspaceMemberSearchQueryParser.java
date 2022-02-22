package io.github.daniil547.user.member;

import io.github.daniil547.common.rest_api_search.SearchQueryParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMemberSearchQueryParser extends SearchQueryParser<WorkspaceMember> {
    @Override
    public Specification<WorkspaceMember> parse(String searchQuery) {
        String query = searchQuery.replaceAll("placeId", "workspaceId");

        return super.parse(query);
    }
}