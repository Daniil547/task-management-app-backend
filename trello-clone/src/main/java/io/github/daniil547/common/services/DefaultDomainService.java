package io.github.daniil547.common.services;

import io.github.daniil547.common.domain.Domain;
import io.github.daniil547.common.repositories.DomainRepository;

import java.util.List;
import java.util.UUID;

public abstract class DefaultDomainService<E extends Domain> implements DomainService<E> {

    @Override
    public void save(E entity) throws Exception {
        getRepository().insert(entity);
    }

    @Override
    public void update(E entity) throws Exception {
        getRepository().update(entity);
    }

    @Override
    public E getById(UUID uuid) throws Exception {
        return getRepository().fetchById(uuid);
    }

    @Override
    public List<E> getAll() throws Exception {
        return getRepository().fetchAll();
    }

    @Override
    public void deleteById(UUID uuid) throws Exception {
        getRepository().deleteById(uuid);
    }

    protected abstract DomainRepository<E> getRepository();

    protected void initEntity(E entity) {
        entity.setId(UUID.randomUUID());
    }
}
