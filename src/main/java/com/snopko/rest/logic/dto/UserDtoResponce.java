package com.snopko.rest.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponce {
    private long id;
    private int idCrypto;
    private String symbol;
    private String username;
    private double price;
}
