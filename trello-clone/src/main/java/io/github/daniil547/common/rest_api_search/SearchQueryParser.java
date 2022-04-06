package io.github.daniil547.common.rest_api_search;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.exceptions.MalformedRestSearchQueryException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * <p>Minimal-effort parser for search queries. Can't handle spaces, special symbols etc in parameters.
 * <p>Needs a refactor
 *
 * @param <E>
 */
@Component
public class SearchQueryParser<E extends Domain> {
    //TODO split WORD pattern into KEY and VALUE patterns
    private static final Pattern WORD = Pattern.compile("([\\w-%]+)");
    private static final Pattern SEARCH_OP = Pattern.compile("(:|!|<|>|~)");
    private static final Pattern BOOLEAN_OP = Pattern.compile("(\\||,)");

    //TODO split parse() into parse(), which should return a list of SearchCriterions,
    // and build(), which should take SearchCriterions and return a built Specification
    public Specification<E> parse(String searchQuery) {
        if (searchQuery == null) {
            return null;
        }
        List<String> words = WORD.matcher(searchQuery)
                                 .results()
                                 .map(MatchResult::group)
                                 .toList();
        List<String> searchOps = SEARCH_OP.matcher(searchQuery)
                                          .results()
                                          .map(MatchResult::group)
                                          .toList();
        List<String> boolOps = BOOLEAN_OP.matcher(searchQuery)
                                         .results()
                                         .map(MatchResult::group)
                                         .toList();

        // check if the query is correct
        if (!(words.size() % 2 == 0 &&
              words.size() / 2 == searchOps.size() &&
              searchOps.size() - 1 == boolOps.size())) {
            throw new MalformedRestSearchQueryException();
        }

        // UUIDs have to be passed as objects
        boolean isUUID = true;

        UUID valueAsUUID = null;
        try {
            valueAsUUID = UUID.fromString(words.get(1));
        } catch (IllegalArgumentException exception) {
            isUUID = false;
        }

        Specification<E> result = new SearchSpecification<>(
                new SearchCriterion(words.get(0),
                                    SearchOperation.fromString(searchOps.get(0)),
                                    isUUID ? valueAsUUID : words.get(1))
        );


        for (int i = 2; i < words.size(); i += 2) {
            try {
                valueAsUUID = UUID.fromString(words.get(i + 1));
            } catch (IllegalArgumentException exception) {
                isUUID = false;
            }

            SearchSpecification<E> tmpSpec = new SearchSpecification<>(
                    new SearchCriterion(words.get(i),
                                        SearchOperation.fromString(searchOps.get(i / 2)),
                                        isUUID ? valueAsUUID : words.get(i + 1))
            );
            result = boolOps.get(i / 2 - 1).equals(",")
                     ? result.and(tmpSpec)
                     : result.or(tmpSpec);
        }

        return result;
    }
}