package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;

import java.util.UUID;

public interface DomainService<E extends Domain> extends Service<E, UUID> {
}
