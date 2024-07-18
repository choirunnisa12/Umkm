package com.example.umkm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;
    private LocalDateTime purchaseTime;}
