package com.example.umkm.controller;

import com.example.umkm.dto.request.WalletRequest;
import com.example.umkm.entity.Wallet;
import com.example.umkm.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("wallets")
    @AllArgsConstructor
    public class WalletController {
        private final WalletService walletService;

        @PostMapping
        public ResponseEntity<Wallet> create(@RequestBody WalletRequest walletRequest) {
            Wallet create = walletService.create(walletRequest);
            return ResponseEntity.ok(create);
        }

        @GetMapping
        public ResponseEntity<List<WalletRequest>> getAll() {
            List<WalletRequest> wallet = walletService.getAll();
            return ResponseEntity.ok(wallet);
        }

        @GetMapping("/{id}")
        public ResponseEntity<WalletRequest> getById(@PathVariable int id) {
            WalletRequest walletRequest = walletService.getById(id);
            return ResponseEntity.ok(walletRequest);
        }

        @PutMapping("/{id}")
        public ResponseEntity<WalletRequest> update(@PathVariable int id, @RequestBody WalletRequest walletRequest) {
            WalletRequest update = walletService.update(id, walletRequest);
            return ResponseEntity.ok(update);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable int id) {
            walletService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }
