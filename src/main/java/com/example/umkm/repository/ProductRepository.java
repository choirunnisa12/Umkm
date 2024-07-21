package com.example.umkm.repository;

import com.example.umkm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM Product p WHERE p.stock < :stockThreshold", nativeQuery = true)
    List<Product> findByStockLessThan(@Param("stockThreshold") Integer stockThreshold);

    @Query(value = "SELECT * FROM products p WHERE p.expire_date < :thresholdDate", nativeQuery = true)
    List<Product> findByExpireDateBefore(@Param("thresholdDate") LocalDate thresholdDate);
}