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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;

    @Override
    public Transaction create(TransactionRequest request) {
        Optional<Product> productNew = productRepository.findById(request.getProductId());
        Optional<Wallet> walletNew = walletRepository.findById(request.getWalletId());

        if (productNew.isPresent() && walletNew.isPresent()) {
            Product product = productNew.get();
            Wallet wallet = walletNew.get();

            // untuk Mengurangi stok produk, kalau stok <0 stok tidak cukup
            int newStock = product.getStock() - request.getQuantity();
            if (newStock < 0) {
                throw new RuntimeException("Not enough stock");
            }
            product.setStock(newStock);
            productRepository.save(product);

            // untuk Membuat transaksi
            Transaction transaction = new Transaction();
            transaction.setPrice(request.getPrice());
            transaction.setQuantity(request.getQuantity());
            transaction.setDescription(request.getDescription());
            transaction.setDateTransaction(LocalDate.now());
            transaction.setProduct(product);
            transaction.setWallet(wallet);
            transaction.calculateTotalPrice();

            // Menambah saldo wallet
            int TotalAdd = transaction.getTotalPrice();
            wallet.setBalance(wallet.getBalance() + TotalAdd);
            walletRepository.save(wallet);

            System.out.println("Updating wallet balance: " + wallet.getBalance());
            walletRepository.save(wallet);

            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Product or Wallet not found");
    }

    //untuk melihat list semua transaksi
    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    //untuk update  transaksi
    @Override
    public Transaction update(Transaction request) {
        Transaction currentTransaction = transactionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Product product = currentTransaction.getProduct();
        Wallet wallet = currentTransaction.getWallet();

        product.setStock(product.getStock() + currentTransaction.getQuantity());
        wallet.setBalance(wallet.getBalance() - currentTransaction.getTotalPrice());

        // Memperbarui transaksi dengan data terbaru
        product.setStock(product.getStock() - request.getQuantity());
        currentTransaction.setPrice(request.getPrice());
        currentTransaction.setQuantity(request.getQuantity());
        currentTransaction.calculateTotalPrice();
        currentTransaction.setDescription(request.getDescription());
        currentTransaction.setDateTransaction(request.getDateTransaction());
        currentTransaction.setProduct(request.getProduct());
        currentTransaction.setWallet(request.getWallet());

        // Menambah saldo wallet dan mengurangi stok produk dengan data terbaru
        wallet.setBalance(wallet.getBalance() + currentTransaction.getTotalPrice());
        productRepository.save(product);
        walletRepository.save(wallet);

        return transactionRepository.save(currentTransaction);
    }

    @Override
    public void delete(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Product product = transaction.getProduct();
        Wallet wallet = transaction.getWallet();

        product.setStock(product.getStock() + transaction.getQuantity());
        productRepository.save(product);

        // untuk mengurangi saldo wallet
        wallet.setBalance(wallet.getBalance() - transaction.getTotalPrice());
        walletRepository.save(wallet);

        transactionRepository.delete(transaction);
    }
}