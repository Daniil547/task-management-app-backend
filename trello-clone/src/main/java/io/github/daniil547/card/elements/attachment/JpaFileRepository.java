package io.github.daniil547.card.elements.attachment;

import io.github.daniil547.common.repositories.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFileRepository extends DomainRepository<File> {
}
