package com.example.umkm.service;

import com.example.umkm.dto.request.WalletRequest;
import java.util.List;

public interface WalletService {
    WalletRequest create(WalletRequest walletRequest);
    List<WalletRequest>getAll ();
    WalletRequest getById(int id);
    WalletRequest update(int id, WalletRequest WalletRequest);
    void delete(int id);
}
