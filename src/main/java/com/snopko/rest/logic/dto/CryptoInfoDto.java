package com.snopko.rest.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoInfoDto {
    private int idCrypto;
    private double price;
}
