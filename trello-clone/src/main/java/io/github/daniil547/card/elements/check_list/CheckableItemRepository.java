package io.github.daniil547.card.elements.check_list;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckableItemRepository extends JpaRepository<CheckableItem, UUID> {
}
