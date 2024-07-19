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

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private ProductRepository productRepository;
    private WalletRepository walletRepository;

    @Override
    public Transaction create(TransactionRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }

        Transaction transaction = new Transaction();
        transaction.setPrice(request.getPrice());
        transaction.setQuantity(request.getQuantity());
        transaction.setTotalPrice(request.getTotalPrice());
        transaction.setDescription(request.getDescription());
        transaction.setDateTransaction(request.getDateTransaction());
        transaction.setProduct(product);

        transactionRepository.save(transaction);

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);
    return null;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction update(Transaction request) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(request.getId());
        if (transactionOpt.isEmpty()) {
            throw new RuntimeException("Transaction not found"); // Handle transaction not found scenario
        }
        Transaction existingTransaction = transactionOpt.get();
        existingTransaction.setPrice(request.getPrice());
        existingTransaction.setQuantity(request.getQuantity());
        existingTransaction.setTotalPrice(request.getPrice() * request.getQuantity());
        existingTransaction.setProduct(request.getProduct());
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void delete(int id) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            Product product = transaction.getProduct();
            product.setStock(product.getStock() + transaction.getQuantity()); // Restock product
            productRepository.save(product);
            transactionRepository.delete(transaction);
        } else {
            throw new RuntimeException("Transaction not found"); // Handle transaction not found scenario
        }
    }
}
