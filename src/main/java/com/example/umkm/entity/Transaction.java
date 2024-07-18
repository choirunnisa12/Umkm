package com.example.umkm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @PrePersist
    public void prePersist() {
        if (totalPrice == null) {
            totalPrice = price * quantity;
        }
    }
}
