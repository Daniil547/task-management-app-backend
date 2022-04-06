package io.github.daniil547.common.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.daniil547.common.util.JsonDtoView;
import lombok.Data;

import java.util.UUID;

@Data
public abstract class DomainDto {
    @JsonView(JsonDtoView.Basic.class)
    private UUID id; // might be null e.g. when entity is to be created
}
