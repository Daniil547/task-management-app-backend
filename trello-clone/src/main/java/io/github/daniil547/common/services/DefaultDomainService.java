package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class DefaultDomainService<E extends Domain> implements DomainService<E> {

    @Override
    public E save(E entity) {
        return getRepository().insert(entity);
    }

    @Override
    public E update(E entity) {
        return getRepository().update(entity);
    }

    @Override
    public Optional<E> getById(UUID uuid) {
        return getRepository().fetchById(uuid);
    }

    @Override
    public List<E> getAll() {
        return getRepository().fetchAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        getRepository().deleteById(uuid);
    }

    protected abstract DomainRepository<E> getRepository();

    protected void initEntity(E entity) {
        entity.setId(UUID.randomUUID());
    }
}
