package com.example.umkm.service.Impl;

import com.example.umkm.dto.request.WalletRequest;
import com.example.umkm.entity.Wallet;
import com.example.umkm.repository.WalletRepository;
import com.example.umkm.service.WalletService;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    private WalletRepository walletRepository;
    private WalletRequest walletRequest;

    @Override
    public WalletRequest create(WalletRequest walletRequest) {
        Wallet wallet = new Wallet();
        wallet.setBalance(walletRequest.getBalance());
        walletRepository.save(wallet);
        return new WalletRequest(wallet.getBalance());
    }
    @Override
    public List<WalletRequest> getAll() {
        return walletRepository.findAll().stream()
                .map(wallet -> new WalletRequest(wallet.getBalance()))
                .collect(Collectors.toList());    }

    @Override
    public WalletRequest getById(int id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow();
        return new WalletRequest(wallet.getBalance());    }

    @Override
    public WalletRequest update(int id, WalletRequest WalletRequest) {
        Wallet wallet = walletRepository.findById(id).orElseThrow();
        wallet.setBalance(WalletRequest.getBalance());
        walletRepository.save(wallet);
        return new WalletRequest(wallet.getBalance());    }

    @Override
    public void delete(int id) {
        walletRepository.deleteById(id);

    }
}
