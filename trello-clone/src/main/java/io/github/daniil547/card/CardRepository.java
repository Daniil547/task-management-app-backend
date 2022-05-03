package io.github.daniil547.card;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface CardRepository extends PageRepository<Card> {
    UUID getCardListIdById(UUID id);
}
