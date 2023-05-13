package com.snopko.rest.dao.repository;

import com.snopko.rest.dao.entity.CryptoInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ICryptoInfoRepository extends CrudRepository<CryptoInfo, Long> {
    boolean existsByIdCrypto(int idCrypto);

    Optional<CryptoInfo> findByIdCrypto(int idCrypto);
}
