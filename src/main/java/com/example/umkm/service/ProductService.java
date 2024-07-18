package com.example.umkm.service;

import com.example.umkm.entity.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    Product create(Product request);
    List<Product> getAll();
    Product getById(Integer id);
    Product update(Integer id, Product request);
    void delete(Integer id);
    List<Product> getExpiringProducts(LocalDate thresholdDate);
    List<Product> search(String name);
    List<Product> getLowStockProducts(Integer stockThreshold);
}

