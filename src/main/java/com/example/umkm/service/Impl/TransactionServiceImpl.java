package com.example.umkm.service.Impl;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;
import com.example.umkm.entity.Wallet;
import com.example.umkm.repository.ProductRepository;
import com.example.umkm.repository.TransactionRepository;
import com.example.umkm.repository.WalletRepository;
import com.example.umkm.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private ProductRepository productRepository;
    private WalletRepository walletRepository;

    @Override
    public Transaction create(TransactionRequest transactionRequest) {
        // Find the Wallet by ID
        Wallet wallet = walletRepository.findById(transactionRequest.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Update Wallet Balance
        wallet.setBalance(wallet.getBalance() + transactionRequest.getTotalPrice());
        walletRepository.save(wallet);

        // Find the Product by ID
        Product product = productRepository.findById(transactionRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and save the Transaction
        Transaction transaction = transactionRequest.create(product, wallet);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(Integer id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public Transaction update(Integer id, TransactionRequest transactionRequest) {
        // Find existing Transaction
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Find the Wallet and Product
        Wallet wallet = walletRepository.findById(transactionRequest.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        Product product = productRepository.findById(transactionRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update Wallet Balance
        wallet.setBalance(wallet.getBalance() + transactionRequest.getTotalPrice() - transaction.getTotalPrice());
        walletRepository.save(wallet);

        // Update Transaction
        transaction.setPrice(transactionRequest.getPrice());
        transaction.setQuantity(transactionRequest.getQuantity());
        transaction.setTotalPrice(transactionRequest.getTotalPrice());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setDateTransaction(transactionRequest.getDateTransaction());
        transaction.setProduct(product);
        transaction.setWallet(wallet);

        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Integer id) {
        // Find the Transaction
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Find the Wallet and update its balance
        Wallet wallet = transaction.getWallet();
        wallet.setBalance(wallet.getBalance() - transaction.getTotalPrice());
        walletRepository.save(wallet);

        // Delete the Transaction
        transactionRepository.delete(transaction);
    }
}