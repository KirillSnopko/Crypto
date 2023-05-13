package com.snopko.rest.dao.repository;

import com.snopko.rest.dao.entity.Crypto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@ConfigurationProperties(prefix = "crypto")
@Component
@Data
public class AvailableCrypto {
    private List<Crypto> list;

    public Crypto getBySymbol(String symbol) {
        return list.stream()
                .filter(i -> i.getSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("invalid symbol: {0}", symbol)));
    }
}
