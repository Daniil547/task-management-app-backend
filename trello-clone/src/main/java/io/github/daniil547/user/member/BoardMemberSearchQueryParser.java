package io.github.daniil547.user.member;

import io.github.daniil547.common.rest_api_search.SearchQueryParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BoardMemberSearchQueryParser extends SearchQueryParser<BoardMember> {
    @Override
    public Specification<BoardMember> parse(String searchQuery) {
        String query = searchQuery.replaceAll("placeId", "boardId");

        return super.parse(query);
    }
}