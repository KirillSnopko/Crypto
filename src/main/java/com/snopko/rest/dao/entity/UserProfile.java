package com.snopko.rest.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private double initPrice;
    @ManyToOne(targetEntity = CryptoInfo.class, fetch = FetchType.LAZY)
    private CryptoInfo cryptoInfo;
}
