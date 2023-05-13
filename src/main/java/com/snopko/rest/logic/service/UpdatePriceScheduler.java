package com.snopko.rest.logic.service;

import com.snopko.rest.dao.entity.CryptoInfo;
import com.snopko.rest.dao.repository.AvailableCrypto;
import com.snopko.rest.dao.repository.ICryptoInfoRepository;
import com.snopko.rest.logic.dto.CryptoProviderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UpdatePriceScheduler {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ICryptoInfoRepository cryptoInfoRepository;
    @Autowired
    private AvailableCrypto availableCrypto;
    private static final Logger logger = LoggerFactory.getLogger(UpdatePriceScheduler.class);

    @Scheduled(fixedRate = 60000)
    public void updatePrice() {
        List<CryptoProviderDto> cryptoProviderDtos = availableCrypto.getList()
                .stream()
                .map(i -> getPrice(i.getId()))
                .collect(Collectors.toList());

        List<CryptoInfo> cryptoInfoList = StreamSupport.stream(cryptoInfoRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());

        logger.info("Triggering a scheduler event...");
        for (CryptoInfo x : cryptoInfoList) {
            double newPrice = cryptoProviderDtos.stream()
                    .filter(i -> i.getId() == x.getIdCrypto())
                    .findFirst()
                    .get()
                    .getPrice_usd();

            double percent = newPrice / x.getPrice();
            x.setPrice(newPrice);
            cryptoInfoRepository.save(x);

            x.getUserProfiles()
                    .stream()
                    .filter(i -> i.getInitPrice() / newPrice >= 1.01 || i.getInitPrice() / newPrice <= 0.99)
                    .forEach(i -> logger.warn(MessageFormat.format("id:{0}, username: {1}, percent:{2}", x.getIdCrypto(), i.getUsername(), (1 - (i.getInitPrice() / newPrice)) * 100)));
        }
    }

    private CryptoProviderDto getPrice(int id) {
        String url = "https://api.coinlore.net/api/ticker/?id={id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<CryptoProviderDto>> entity = new HttpEntity<>(headers);
        CryptoProviderDto[] str = restTemplate.exchange(url, HttpMethod.GET, entity, CryptoProviderDto[].class, id).getBody();
        return Arrays.stream(str).findFirst().get();
    }
}
