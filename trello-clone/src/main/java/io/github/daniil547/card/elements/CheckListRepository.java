package io.github.daniil547.card.elements;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends DomainRepository<CheckList> {
}
