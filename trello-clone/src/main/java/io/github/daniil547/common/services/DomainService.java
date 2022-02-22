package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.repositories.DomainRepository;
import io.github.daniil547.common.rest_api_search.SearchQueryParser;

public interface DomainService<D extends DomainDto, E extends Domain> {


    DomainRepository<E> repository();

    SearchQueryParser<E> searchQueryParser();
}
