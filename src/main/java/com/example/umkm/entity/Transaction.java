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
    private int id;
    private int price;
    private int quantity;
    private int totalPrice;
    private String description;
    private LocalDate dateTransaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    public void setPrice(int price) {
        this.price = price;
        calculateTotalPrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        this.totalPrice = this.price * this.quantity;
    }
}
