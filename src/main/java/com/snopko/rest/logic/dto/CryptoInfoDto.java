package com.snopko.rest.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoInfoDto extends RepresentationModel<CryptoInfoDto> {
    private int idCrypto;
    private double price;
}
