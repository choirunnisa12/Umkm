package com.example.umkm.controller;

import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;
import com.example.umkm.repository.ProductRepository;
import com.example.umkm.repository.TransactionRepository;
import com.example.umkm.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionService transactionService;
    private ProductRepository productRepository;
    private TransactionRepository transactionRepository;
    private Transaction transaction;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction, @RequestParam Integer productId) {
        Transaction create = transactionService.create(transaction, productId);
        return create != null ? ResponseEntity.ok(create) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Integer id) {
        Transaction transaction = transactionService.getById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.update(transaction);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void delete(Integer id) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            Product product = transaction.getProduct();
            product.setStock(product.getStock() + transaction.getQuantity()); // Restock product
            productRepository.save(product);
            transactionRepository.delete(transaction);
        }
    }
}
