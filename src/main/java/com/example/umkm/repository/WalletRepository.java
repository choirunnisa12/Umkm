package com.example.umkm.repository;
import com.example.umkm.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("SELECT w FROM Wallet w WHERE w.balance < :balanceThreshold")
    List<Wallet> findByBalanceLessThan(@Param("balanceThreshold") int balanceThreshold);

}
