package com.example.umkm.controller;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;
import com.example.umkm.repository.ProductRepository;
import com.example.umkm.repository.TransactionRepository;
import com.example.umkm.service.TransactionService;
import com.example.umkm.utils.WebResponse;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Data
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private  TransactionService transactionService;
    private  ProductRepository productRepository;
    private  TransactionRepository transactionRepository;
    private  Transaction transaction;

    @PostMapping
    public Transaction create(@RequestBody TransactionRequest request) {
        return transactionService.create(request);
    }
    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

    @PutMapping
    public ResponseEntity<Transaction> update(@RequestBody Transaction request) {
        Transaction update = transactionService.update(transaction);
        return update != null ? ResponseEntity.ok(update) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        transactionService.delete(id);
        return "Transaction successfully deleted";

    }
}
