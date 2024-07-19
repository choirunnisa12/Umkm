package com.example.umkm.dto.request;

import com.example.umkm.entity.Transaction;
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
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
    private String description;
    private LocalDate dateTransaction;
    private int productId; // Tambahkan field ini

    public Transaction create() {
        return Transaction.builder()
                .price(price)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
        public Transaction toTransaction() {
            Transaction transaction = new Transaction();
            transaction.setPrice(this.price);
            transaction.setQuantity(this.quantity);
            transaction.setTotalPrice(this.totalPrice);
            transaction.setDescription(this.description);
            return transaction;
    }
}
