package com.eyecare.application.controller;

import com.eyecare.application.dao.order.Order;
import com.eyecare.application.event.handler.model.OrderCreatedEvent;
import com.eyecare.application.event.handler.producer.OrderEventProducer;
import com.eyecare.application.model.OrderRequest;
import com.eyecare.application.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderEventProducer producer;
    private final OrderService orderService;

    public OrderController(OrderEventProducer producer, OrderService orderService) {
        this.producer = producer;
        this.orderService = orderService;
    }

    @PostMapping("/raiseOrder")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> raiseOrder(@RequestBody OrderRequest request) {
        var event = new OrderCreatedEvent();
        event.setOrderId(String.valueOf(UUID.randomUUID()));
        event.setCustomerId(request.getCustomerId());
        event.setFrameType(request.getFrameType());
        event.setLensType(request.getLensType());
        event.setOrderTimestamp(Instant.now());

        producer.publishOrderCreated(event);

        orderService.raiseOrder(Order.toOrderEntity(event));

        return ResponseEntity.accepted().body("Order has been raised, Order ID: " + event.getOrderId());
    }

    @GetMapping("/getOrderDetailsStatus")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Order>> getOrderDetails(@RequestParam String orderId) {
        return ResponseEntity.accepted().body(
                orderService.getOrderDetails(orderId));

    }

}
