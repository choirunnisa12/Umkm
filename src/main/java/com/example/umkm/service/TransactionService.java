package com.example.umkm.service;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequest transactionRequest);
    List<Transaction> getAll();
    Transaction getById(Integer id);
    Transaction update(Integer id, TransactionRequest transactionRequest);
    void delete(Integer id);
}
