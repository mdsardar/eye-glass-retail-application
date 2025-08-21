package com.eyecare.application.service.inventory;

import com.eyecare.application.dao.inventory.entity.ItemType;
import com.eyecare.application.dao.inventory.repository.InventoryItemRepository;
import com.eyecare.application.service.supplier.SupplierIntegrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class InventoryService {
    private final InventoryItemRepository repo;
    private final SupplierIntegrationService supplier;

    public InventoryService(
            InventoryItemRepository repo,
            SupplierIntegrationService supplier) {
        this.repo = repo;
        this.supplier = supplier;
    }

    @Transactional
    public Mono<Boolean> reserveAndReorderIfNeeded(ItemType type, int neededQty) {
        return repo.findByType(type)
                .map(item -> {
                    int remaining = item.getQuantity() - neededQty;
                    item.setQuantity(Math.max(remaining, 0));
                    repo.save(item);

                    if (remaining < item.getReorderThreshold()) {
                        // trigger async reorder
                        return supplier.reorder(type, item.getReorderThreshold() * 2)
                                .thenReturn(false);  // out of stock scenario
                    }
                    return Mono.just(true);     // in-stock scenario
                })
                .orElseGet(() -> Mono.just(false));
    }

}
