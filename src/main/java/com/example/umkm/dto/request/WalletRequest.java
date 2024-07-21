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

    @Schema(description = "Name of the wallet", example = "Bank")
    private int balance;
    @Schema(description = "Name of the wallet", example = "Bank BCA")
    private String name;

    public Wallet create() {
        return Wallet.builder()
                .id(id)
                .balance(balance)
                .name(name)
                .build();
    }
}

