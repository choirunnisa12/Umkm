package com.example.umkm.service;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequest request);
    List<Transaction> findAll();
    Transaction update(Transaction request);
    void delete(int id);

}
