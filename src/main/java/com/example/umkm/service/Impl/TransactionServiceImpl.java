package com.example.umkm.service.Impl;

import com.example.umkm.entity.Product;
import com.example.umkm.entity.Transaction;
import com.example.umkm.repository.ProductRepository;
import com.example.umkm.repository.TransactionRepository;
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

    @Override
    public Transaction create(Transaction request, Integer productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            request.setProduct(product);
            request.setTotalPrice(request.getPrice() * request.getQuantity());

            // Update product stock
            product.setStock(product.getStock() - request.getQuantity());
            productRepository.save(product);

            return transactionRepository.save(request);
        }
        return null;    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction update(Transaction request) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(request.getId());
        if (transactionOpt.isPresent()) {
            Transaction existingTransaction = transactionOpt.get();
            existingTransaction.setPrice(request.getPrice());
            existingTransaction.setQuantity(request.getQuantity());
            existingTransaction.setTotalPrice(request.getPrice() * request.getQuantity());
            existingTransaction.setProduct(request.getProduct());
            return transactionRepository.save(existingTransaction);
        }
        return null;
    }

    @Override
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
