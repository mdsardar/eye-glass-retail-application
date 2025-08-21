package com.eyecare.application.dao.inventory.repository;

import com.eyecare.application.dao.inventory.entity.InventoryItem;
import com.eyecare.application.dao.inventory.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryItemRepository
        extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByType(ItemType type);
}
