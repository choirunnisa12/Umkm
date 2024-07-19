package com.example.umkm.dto.request;

import com.example.umkm.entity.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
    public class WalletRequest {
    private int id;

    @Schema(description = "Balance of the wallet", example = "10000")
    private int balance;

    public WalletRequest(){}
    public WalletRequest(int balance) {
        this.balance = balance;
    }
}

