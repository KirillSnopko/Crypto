package com.snopko.rest.config;

import com.snopko.rest.dao.entity.Crypto;
import com.snopko.rest.dao.entity.CryptoInfo;
import com.snopko.rest.dao.repository.AvailableCrypto;
import com.snopko.rest.dao.repository.ICryptoInfoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebConfig {
    @Autowired
    private AvailableCrypto availableCrypto;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);

        return mapper;
    }

    @Bean
    CommandLineRunner initDatabase(ICryptoInfoRepository repository) {
        List<Crypto> cryptoList = availableCrypto.getList();
        List<CryptoInfo> cryptoInfoList = cryptoList.stream().map(i -> new CryptoInfo(i.getId())).collect(Collectors.toList());

        return args -> {
            cryptoInfoList.stream()
                    .filter(i -> !repository.existsByIdCrypto(i.getIdCrypto()))
                    .forEach(i -> repository.save(i));
        };
    }
}
