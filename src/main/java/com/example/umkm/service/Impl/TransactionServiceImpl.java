package com.example.umkm.service.Impl;

import com.example.umkm.dto.request.TransactionRequest;
import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;
import com.example.umkm.entity.Wallet;
import com.example.umkm.repository.ProductRepository;
import com.example.umkm.repository.TransactionRepository;
import com.example.umkm.repository.WalletRepository;
import com.example.umkm.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public Transaction create(TransactionRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        Optional<Wallet> walletOpt = walletRepository.findById(request.getWalletId());

        if (productOpt.isPresent() && walletOpt.isPresent()) {
            Product product = productOpt.get();
            Wallet wallet = walletOpt.get();

            // Mengurangi stok produk
            product.setStock(product.getStock() - request.getQuantity());
            productRepository.save(product);

            // Memperbarui saldo wallet
            wallet.setBalance(wallet.getBalance() + (request.getPrice() * request.getQuantity()));
            walletRepository.save(wallet);

            // Membuat transaksi
            Transaction transaction = request.create(product, wallet);
            return transactionRepository.save(transaction);
        }
        throw new RuntimeException("Product or Wallet not found");
    }
    @Override
    @Transactional
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Transaction update(Transaction request) {
        Transaction existingTransaction = transactionRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existingTransaction.setPrice(request.getPrice());
        existingTransaction.setQuantity(request.getQuantity());
        existingTransaction.setTotalPrice(request.getPrice() * request.getQuantity());
        existingTransaction.setDescription(request.getDescription());
        existingTransaction.setDateTransaction(request.getDateTransaction());
        existingTransaction.setProduct(request.getProduct());

        return transactionRepository.save(existingTransaction);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Product product = transaction.getProduct();
        product.setStock(product.getStock() + transaction.getQuantity());
        productRepository.save(product);

        transactionRepository.delete(transaction);
    }
}
