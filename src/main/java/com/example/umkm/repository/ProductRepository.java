package com.example.umkm.repository;

import com.example.umkm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String name);
    @Query(value = "SELECT * FROM Product p WHERE p.stock < :stockThreshold", nativeQuery = true)
    List<Product> findLowStockProducts(@Param("stockThreshold") Integer stockThreshold);

    @Query(value = "SELECT * FROM Product p WHERE p.expiry_date < :expiryDate", nativeQuery = true)
    List<Product> findExpiringProducts(@Param("expiryDate") LocalDate expiryDate);
}
