package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.dto.DomainDto;

public abstract class DomainConverter<D extends DomainDto, E extends Domain> implements Converter<D, E> {

    @Override
    public E entityFromDto(D dto) {
        E entity = transferEntitySpecificFieldsFromDto(dto);
        transferCommonFieldsFromDto(dto, entity);

        return entity;
    }

    @Override
    public D dtoFromEntity(E entity) {
        D dto = transferDtoSpecificFieldsFromEntity(entity);
        transferCommonFieldsFromEntity(entity, dto);

        return dto;
    }

    protected void transferCommonFieldsFromDto(D dto, E toEntity) {
        toEntity.setId(dto.getId());
    }

    protected void transferCommonFieldsFromEntity(E entity, D toDto) {
        toDto.setId(entity.getId());
    }

    protected abstract E transferEntitySpecificFieldsFromDto(D dto);

    protected abstract D transferDtoSpecificFieldsFromEntity(E entity);

}