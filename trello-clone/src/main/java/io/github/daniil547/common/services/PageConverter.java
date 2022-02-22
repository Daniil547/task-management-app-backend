package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Page;
import io.github.daniil547.common.dto.PageDto;

public abstract class PageConverter<D extends PageDto, E extends Page> extends DomainConverter<D, E> {

    protected void transferCommonFieldsFromDto(D dto, E toEntity) {
        super.transferCommonFieldsFromDto(dto, toEntity);

        toEntity.setPageName(dto.getPageName());
        toEntity.setPageTitle(dto.getPageTitle());
        toEntity.setPageDescription(dto.getPageDescription());

    }

    protected void transferCommonFieldsFromEntity(E entity, D toDto) {
        super.transferCommonFieldsFromEntity(entity, toDto);

        toDto.setPageName(entity.getPageName());
        toDto.setPageTitle(entity.getPageTitle());
        toDto.setPageDescription(entity.getPageDescription());
    }
}