package com.eyecare.application.dao.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class InventoryItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType type;  // FRAME or LENS

    private int quantity;
    private int reorderThreshold;
}