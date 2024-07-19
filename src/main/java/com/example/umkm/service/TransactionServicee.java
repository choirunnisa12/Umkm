package com.example.umkm.service;

import com.example.umkm.entity.Transaction;

import java.util.List;

    public interface TransactionServicee {
        Transaction create(Transaction request, Integer product_id);
        List<Transaction> getAll();
        Transaction getById(Integer id);
        Transaction update(Transaction request);
        void delete(Integer id);
    }

