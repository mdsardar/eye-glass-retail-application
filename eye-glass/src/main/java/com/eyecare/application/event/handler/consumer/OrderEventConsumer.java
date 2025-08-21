package com.eyecare.application.event.handler.consumer;

import com.eyecare.application.dao.inventory.entity.ItemType;
import com.eyecare.application.event.handler.model.OrderCreatedEvent;
import com.eyecare.application.service.constant.OrderStatus;
import com.eyecare.application.service.inventory.InventoryService;
import com.eyecare.application.service.order.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OrderEventConsumer {

    private final InventoryService inventoryService;
    private final OrderService orderService;

    public OrderEventConsumer(
            InventoryService inventoryService,
            OrderService orderService) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "eyeglass-orders", groupId = "eyeglass-order-group")
    public void handleOrderCreated(OrderCreatedEvent event) {

        // Persist initial RECEIVED status
        orderService.updateStatus(event.getOrderId(), OrderStatus.RECEIVED);

        // Parallel inventory checks
        // Frames/Lens are hardcoded here only for testing purpose
        Mono<Boolean> frameOk = inventoryService
                .reserveAndReorderIfNeeded(
                        ItemType.valueOf("FRAME"), 1
                );
        Mono<Boolean> lensOk = inventoryService
                .reserveAndReorderIfNeeded(
                        ItemType.valueOf("LENS"), 1
                );

        // When both checks complete, decide next status
        Mono.zip(frameOk, lensOk)
                .doOnNext(tuple -> {
                    boolean bothAvailable = tuple.getT1() && tuple.getT2();
                    OrderStatus nextStatus = bothAvailable
                            ? OrderStatus.PROCESSING
                            : OrderStatus.PENDING_REORDER;

                    orderService.updateStatus(event.getOrderId(), nextStatus);
                })
                .subscribe();
        }
    }
