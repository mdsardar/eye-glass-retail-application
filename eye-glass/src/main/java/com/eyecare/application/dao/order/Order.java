package com.eyecare.application.dao.order;

import com.eyecare.application.event.handler.model.OrderCreatedEvent;
import com.eyecare.application.service.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Order {

    @Id
    @Column(name = "orderId", nullable = false)
    private String orderId;
    private String customerId;
    private String frameType;
    private String lensType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // RECEIVED, PROCESSING, PENDING_REORDER, COMPLETED

    public static Order toOrderEntity(OrderCreatedEvent event) {
        Order order = new Order();
        order.setOrderId(event.getOrderId());
        order.setCustomerId(event.getCustomerId());
        order.setFrameType(event.getFrameType());
        order.setLensType(event.getLensType());
        return order;
    }
}
