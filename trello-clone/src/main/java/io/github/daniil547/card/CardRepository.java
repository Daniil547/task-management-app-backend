package io.github.daniil547.card;

import io.github.daniil547.common.repositories.PageRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CardRepository extends PageRepository<Card> {
}
