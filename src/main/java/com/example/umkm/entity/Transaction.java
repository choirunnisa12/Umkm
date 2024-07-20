package com.example.umkm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import java.time.LocalDate;
@Builder
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
    private String description;
    private LocalDate dateTransaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @PrePersist
    public void prePersist() {
        if (totalPrice == null) {
            totalPrice = price * quantity;
        }
    }
}
