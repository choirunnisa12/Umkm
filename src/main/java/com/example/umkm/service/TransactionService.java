package com.example.umkm.service;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequest transactionRequest);
    List<TransactionRequest> getAll();
    TransactionRequest getById(int id);
    TransactionRequest update(int id,TransactionRequest transactionRequest);
    void delete(int id);

}
