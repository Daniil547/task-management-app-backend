package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.dto.DomainDto;

public interface Converter<D extends DomainDto, E extends Domain> {
    E entityFromDto(D dto);

    D dtoFromEntity(E entity);
}
