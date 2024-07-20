package com.example.umkm.controller;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Transaction;
import com.example.umkm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    // Create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.create(transactionRequest);
        return ResponseEntity.ok(transaction);
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        List<Transaction> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    // Get a transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Integer id) {
        Transaction transaction = transactionService.getById(id);
        return ResponseEntity.ok(transaction);
    }

    // Update a transaction by ID
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Integer id, @RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.update(id, transactionRequest);
        return ResponseEntity.ok(transaction);
    }

    // Delete a transaction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
