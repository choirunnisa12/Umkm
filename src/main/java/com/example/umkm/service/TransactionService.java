package com.example.umkm.service;

import com.example.umkm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(Transaction request, Integer productId);
    List<Transaction> getAll();
    Transaction getById(Integer id);
    Transaction update(Transaction request);
    void delete(Integer id);
}
