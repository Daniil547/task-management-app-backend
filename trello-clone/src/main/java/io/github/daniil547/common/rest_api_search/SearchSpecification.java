package io.github.daniil547.common.rest_api_search;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.exceptions.MalformedRestSearchQueryException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class SearchSpecification<E extends Domain> implements Specification<E> {

    private SearchCriterion criterion;

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        try {
            return switch (criterion.getOperation()) {
                case EQUALITY -> builder.equal(root.get(criterion.getKey()),
                                               criterion.getValue());
                case INEQUALITY -> builder.notEqual(root.get(criterion.getKey()),
                                                    criterion.getValue());
                case GREATER_THAN -> builder.greaterThan(root.get(criterion.getKey()),
                                                         criterion.getValue().toString());
                case LESS_THAN -> builder.lessThan(root.get(criterion.getKey()),
                                                   criterion.getValue().toString());
                case LIKE -> builder.like(root.get(criterion.getKey()),
                                          criterion.getValue().toString());
            };
        } catch (IllegalArgumentException e) {
            throw new MalformedRestSearchQueryException("Search query mentions fields that aren't valid for the desired resource");
        }
    }
}