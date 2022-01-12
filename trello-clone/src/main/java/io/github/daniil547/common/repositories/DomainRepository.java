package io.github.daniil547.common.repositories;

import io.github.daniil547.common.domain.Domain;

import java.util.UUID;

public interface DomainRepository<E extends Domain> extends Repository<E, UUID> {
}
