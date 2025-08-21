package com.eyecare.application.dao.order.repository;

import com.eyecare.application.dao.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<Order, String> {
}