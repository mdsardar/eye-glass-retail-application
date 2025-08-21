package com.eyecare.application.service.order;

import com.eyecare.application.dao.order.Order;
import com.eyecare.application.service.constant.OrderStatus;

import java.util.Optional;

public interface OrderService {

    // This reusable entity can be used for order reconciliation, return tracking etc.,

    void raiseOrder(Order order);

    void updateStatus(String orderId, OrderStatus status);

    Optional<Order> getOrderDetails(String orderId);

}
