package com.snopko.rest.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoDto extends RepresentationModel<CryptoDto> {
    private int id;
    private String symbol;
}
