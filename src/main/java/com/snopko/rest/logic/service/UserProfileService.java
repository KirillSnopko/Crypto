package com.snopko.rest.logic.service;

import com.snopko.rest.dao.entity.Crypto;
import com.snopko.rest.dao.entity.CryptoInfo;
import com.snopko.rest.dao.entity.UserProfile;
import com.snopko.rest.dao.repository.AvailableCrypto;
import com.snopko.rest.dao.repository.ICryptoInfoRepository;
import com.snopko.rest.dao.repository.IUserProfileRepository;
import com.snopko.rest.logic.dto.UserDto;
import com.snopko.rest.logic.dto.UserDtoResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private IUserProfileRepository repository;
    @Autowired
    private AvailableCrypto availableCrypto;
    @Autowired
    private ICryptoInfoRepository cryptoInfoRepository;

    public UserDtoResponce add(UserDto dto) {

        Crypto crypto = availableCrypto.getBySymbol(dto.getSymbol());
        CryptoInfo cryptoInfo = cryptoInfoRepository.findByIdCrypto(crypto.getId()).get();

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(dto.getUsername());
        userProfile.setCryptoInfo(cryptoInfo);
        userProfile.setInitPrice(cryptoInfo.getPrice());
        userProfile = repository.save(userProfile);

        UserDtoResponce responce = new UserDtoResponce();
        responce.setIdCrypto(userProfile.getCryptoInfo().getIdCrypto());
        responce.setSymbol(crypto.getSymbol());
        responce.setUsername(userProfile.getUsername());
        responce.setPrice(userProfile.getInitPrice());

        return responce;
    }
}
