package com.example.umkm.service.Impl;

import com.example.umkm.entity.Product;
import com.example.umkm.repository.ProductRepository;
import com.example.umkm.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Override
    public Product create(Product request) {
        return productRepository.saveAndFlush(request);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product update(Integer id, Product request) {
        Product existingProduct = this.getById(id);
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());
        existingProduct.setExpireDate(request.getExpireDate());
        return productRepository.save(existingProduct);

    }
    //untuk menghapus product
    @Override
    public void delete(Integer id) {
        this.getById(id);
        productRepository.deleteById(id);
    }

    //untuk mencari product berdasarkan kapan expire
    @Override
    public List<Product> getExpiringProducts(LocalDate thresholdDate) {
        return productRepository.findByExpireDateBefore(thresholdDate);
    }
    //untuk mencari nama product
    @Override
    public List<Product> search(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    //untuk mencari tinggal berapa stock product
    @Override
    public List<Product> getLowStockProducts(Integer stockThreshold) {
        return productRepository.findAll().stream()
                .filter(product -> product.getStock() < stockThreshold)
                .collect(Collectors.toList());
    }
}