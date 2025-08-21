package com.eyecare.application.service.supplier;

import com.eyecare.application.dao.inventory.entity.ItemType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SupplierIntegrationService {

    private final WebClient webClient;

    public SupplierIntegrationService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://supplier.example.com").build();
    }

    public Mono<Void> reorder(ItemType type, int quantity) {
        return webClient.post()
                .uri("/api/reorders")
                .bodyValue(new ReorderRequest(type, quantity))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(v -> // To-do Logger Integration pending
                        System.out.println("Reorder placed for " + type + " x" + quantity)
                );
    }

    // DTO for external call
    static record ReorderRequest(ItemType type, int quantity) {}
}

