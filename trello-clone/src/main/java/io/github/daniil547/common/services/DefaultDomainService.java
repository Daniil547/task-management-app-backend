package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.dto.DomainDto;
import io.github.daniil547.common.rest_api_search.SearchQueryParser;
import io.github.daniil547.common.validation.CustomValidator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class DefaultDomainService<D extends DomainDto, E extends Domain> implements DomainService<D, E> {
    @Setter(onMethod_ = @Autowired(required = false))
    List<CustomValidator<D>> validators;
    @Setter(onMethod_ = @Autowired)
    private SearchQueryParser<E> searchQueryParser;

    @Override
    public void validate(D dto) {
        if (validators != null) {
            validators.forEach(v -> v.validate(dto));
        }
    }

    @Override
    public SearchQueryParser<E> searchQueryParser() {
        return this.searchQueryParser;
    }
}
