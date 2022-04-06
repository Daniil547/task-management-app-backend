package io.github.daniil547.common.validation;

import io.github.daniil547.common.dto.DomainDto;

public interface CustomValidator<D extends DomainDto> {
    public void validate(D dto);
}
