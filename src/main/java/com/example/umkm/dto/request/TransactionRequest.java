package com.example.umkm.dto.request;

import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;
import com.example.umkm.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private int price;
    private int quantity;
    private int totalPrice;
    private String description;
    private LocalDate dateTransaction;
    private int productId; // Tambahkan field ini
    private int walletId;

    public Transaction create() {
        return Transaction.builder()
                .price(price)
                .quantity(quantity)
                .totalPrice(price * quantity)
                .description(description)
                .dateTransaction(dateTransaction)
                .build();
    }
    public Transaction create(Product product, Wallet wallet) {
        return Transaction.builder()
                .price(price)
                .quantity(quantity)
                .totalPrice(price * quantity)
                .description(description)
                .dateTransaction(dateTransaction)
                .product(product)
                .wallet(wallet)
                .build();
    }
}
