package io.github.daniil547.card.elements;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckableItemRepository extends JpaRepository<CheckableItem, UUID> {
}
