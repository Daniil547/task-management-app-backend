package io.github.daniil547.card_list;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardListRepository extends PageRepository<CardList> {
    UUID getBoardIdById(UUID id);
}
