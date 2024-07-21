package com.example.umkm.controller;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Transaction;
import com.example.umkm.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.create(request);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll() {
        List<Transaction> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping
    public ResponseEntity<Transaction> update(@RequestBody Transaction request) {
        Transaction transaction = transactionService.update(request);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        transactionService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Transaction>> getTransactionsByPriceGreaterThan(
            @RequestParam("priceThreshold") int priceThreshold) {
        List<Transaction> transactions = transactionService.getTransactionsByPriceGreaterThan(priceThreshold);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}