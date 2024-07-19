package com.example.umkm.controller;
import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Transaction;
import com.example.umkm.service.TransactionService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionRequest transactionRequest) {
        Transaction create = transactionService.create(transactionRequest);
        return ResponseEntity.ok(create);
    }

    @GetMapping
    public ResponseEntity<List<TransactionRequest>> getAll() {
        List<TransactionRequest> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionRequest> getById(@PathVariable int id) {
        TransactionRequest transactionRequest = transactionService.getById(id);
        return ResponseEntity.ok(transactionRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionRequest> update(@PathVariable int id, @RequestBody TransactionRequest transactionRequest) {
        TransactionRequest update = transactionService.update(id, transactionRequest);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
