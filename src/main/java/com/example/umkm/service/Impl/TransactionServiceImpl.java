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

    @Override
    public Transaction create(TransactionRequest transactionRequest) {
        Product product = productRepository.findById(transactionRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Buat transaksi dengan produk
        Transaction transaction = transactionRequest.create(product);

        // Periksa stok produk
        if (product.getStock() < transaction.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // Kurangi stok produk
        product.setStock(product.getStock() - transaction.getQuantity());
        productRepository.save(product);

        // Simpan transaksi
        return transactionRepository.save(transaction);

}

    @Override
    public List<TransactionRequest> getAll() {
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionRequest(
                        transaction.getPrice(),
                        transaction.getQuantity(),
                        transaction.getTotalPrice(),
                        transaction.getDescription(),
                        transaction.getDateTransaction(),
                        transaction.getProduct().getId()
                ))
                .collect(Collectors.toList());    }

    @Override
    public TransactionRequest getById(int id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow();
        return new TransactionRequest(
                transaction.getPrice(),
                transaction.getQuantity(),
                transaction.getTotalPrice(),
                transaction.getDescription(),
                transaction.getDateTransaction(),
                transaction.getProduct().getId()
        );
    }

    @Override
    public TransactionRequest update(int id, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow();
        transaction.setPrice(transactionRequest.getPrice());
        transaction.setQuantity(transactionRequest.getQuantity());
        transaction.setTotalPrice(transactionRequest.getTotalPrice());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setDateTransaction(transactionRequest.getDateTransaction());

        Product product = productRepository.findById(transactionRequest.getProductId()).orElseThrow();
        product.setStock(product.getStock() - transactionRequest.getQuantity());
        productRepository.save(product);

        transaction.setProduct(product);
        transactionRepository.save(transaction);

        return transactionRequest;
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
