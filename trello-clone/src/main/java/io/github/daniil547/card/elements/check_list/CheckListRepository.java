package io.github.daniil547.card.elements.check_list;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends DomainRepository<CheckList> {
}
