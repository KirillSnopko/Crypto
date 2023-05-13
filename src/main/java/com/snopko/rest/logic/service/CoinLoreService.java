package com.snopko.rest.logic.service;

import com.snopko.rest.dao.repository.AvailableCrypto;
import com.snopko.rest.dao.repository.ICryptoInfoRepository;
import com.snopko.rest.logic.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinLoreService {
    @Autowired
    private AvailableCrypto availableCrypto;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ICryptoInfoRepository cryptoInfoRepository;

    public List<CryptoDto> getAvailableCryptos() {
        return availableCrypto.getList()
                .stream()
                .map(i -> mapper.map(i, CryptoDto.class))
                .collect(Collectors.toList());
    }

    public CryptoInfoDto getCurrentPrice(int id) {
        return mapper.map(
                cryptoInfoRepository.findByIdCrypto(id)
                        .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("invalid id: {0}", id))),
                CryptoInfoDto.class);
    }
}
