package com.snopko.rest.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponce extends RepresentationModel<UserDtoResponce> {
    private long id;
    private int idCrypto;
    private String symbol;
    private String username;
    private double price;
}
