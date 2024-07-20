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

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    @Override
    public Wallet create(WalletRequest walletRequest) {
        Wallet wallet = Wallet.builder()
                .name(walletRequest.getName())
                .balance(0) // Set initial balance to 0
                .build();
        return walletRepository.save(wallet);
    }
    @Override
    public List<WalletRequest> getAll() {
        return walletRepository.findAll().stream()
                .map(wallet -> WalletRequest.builder()
                        .id(wallet.getId())
                        .name(wallet.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public WalletRequest getById(int id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return WalletRequest.builder()
                .id(wallet.getId())
                .name(wallet.getName())
                .build();
    }

    @Override
    public WalletRequest update(int id, WalletRequest walletRequest) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setName(walletRequest.getName());
        wallet.setBalance(walletRequest.getBalance());
        Wallet updatedWallet = walletRepository.save(wallet);
        return WalletRequest.builder()
                .id(updatedWallet.getId())
                .name(updatedWallet.getName())
                .build();
    }

    @Override
    public void delete(int id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        walletRepository.delete(wallet);
    }
}