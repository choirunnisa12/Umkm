package com.example.umkm.repository;

import com.example.umkm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "SELECT * FROM transactions t WHERE t.wallet_id = :walletId", nativeQuery = true)
    List<Transaction> findByPriceGreaterThan(@Param("walletId") Integer walletId);

    @Query(value = "SELECT * FROM transactions t WHERE t.date_transaction BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Transaction> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}