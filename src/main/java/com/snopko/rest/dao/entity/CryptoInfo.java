package com.snopko.rest.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CryptoInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int idCrypto;
    private double price;
    @OneToMany(fetch = FetchType.EAGER , targetEntity = UserProfile.class, mappedBy = "cryptoInfo", cascade = CascadeType.ALL)
    private List<UserProfile> userProfiles;

    public CryptoInfo(int idCrypto) {
        this.idCrypto = idCrypto;
        this.price = 0;
    }
}
