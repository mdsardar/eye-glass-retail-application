package com.eyecare.application.service.order;

import com.eyecare.application.dao.order.Order;
import com.eyecare.application.dao.order.repository.OrderRepository;
import com.eyecare.application.service.constant.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repo;

    public OrderServiceImpl(OrderRepository repo) {
        this.repo = repo;
    }

    @Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void raiseOrder(Order order) {
        repo.save(order);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void updateStatus(String orderId, OrderStatus status) {
        repo.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            repo.save(order);
        });
    }

    @Override
    public Optional<Order> getOrderDetails(String orderId) {
        return repo.findById(orderId);
    }
}

