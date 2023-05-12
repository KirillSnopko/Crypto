package com.snopko.rest.logic.service;

import com.snopko.rest.dao.entity.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinLoreService {
    @Autowired
    private RestTemplate restTemplate;

    public double getPrice(Crypto crypto) {
        String url = "https://api.coinlore.net/api/ticker/?id={id}";
        ResponseEntity<?> response = restTemplate.getForObject(url, Double.class, crypto.getId());
    }
}
