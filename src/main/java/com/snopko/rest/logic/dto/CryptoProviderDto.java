package com.snopko.rest.logic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoProviderDto {
    private int id;
    private String symbol;
    private String name;
    private String nameid;
    private int rank;
    private double price_usd;
    private double percent_change_24h;
    private double percent_change_1h;
    private double percent_change_7d;
    private double market_cap_usd;
    private double volume24;
    private double volume24_native;
    private double csupply;
    private double price_btc;
    private double tsupply;
    private double msupply;
}
