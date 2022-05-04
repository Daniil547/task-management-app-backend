package io.github.daniil547.card;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends PageRepository<Card> {

    Optional<Card> findByReminderId(UUID reminderId);

    UUID getCardListIdById(UUID id);
}
