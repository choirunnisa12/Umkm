package com.example.umkm.service;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequest request);
    List<Transaction> getAll();
    Transaction update(Transaction request);
    void delete(int id);
    List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate);
    List<Transaction> getTransactionsByPriceGreaterThan(int priceThreshold);
}