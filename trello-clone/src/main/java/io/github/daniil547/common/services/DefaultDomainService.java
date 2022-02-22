package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.rest_api_search.SearchQueryParser;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DefaultDomainService<D extends DomainDto, E extends Domain> implements DomainService<D, E> {
    @Autowired
    private SearchQueryParser<E> searchQueryParser;

    @Override
    public SearchQueryParser<E> searchQueryParser() {
        return this.searchQueryParser;
    }
}
