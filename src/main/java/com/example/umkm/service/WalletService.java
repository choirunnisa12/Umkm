package com.example.umkm.service;

import com.example.umkm.dto.request.WalletRequest;
import com.example.umkm.entity.Wallet;

import java.util.List;

public interface WalletService {
    Wallet create(WalletRequest walletRequest);
    List<WalletRequest>getAll ();
    WalletRequest getById(int id);
    WalletRequest update(int id, WalletRequest walletRequest);
    void delete(int id);
}
