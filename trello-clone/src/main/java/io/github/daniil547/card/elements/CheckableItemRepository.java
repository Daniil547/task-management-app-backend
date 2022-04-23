package io.github.daniil547.card.elements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CheckableItemRepository extends JpaRepository<CheckableItem, UUID> {
}
