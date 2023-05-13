package com.snopko.rest.controller;

import com.snopko.rest.logic.dto.CryptoDto;
import com.snopko.rest.logic.dto.CryptoInfoDto;
import com.snopko.rest.logic.dto.UserDto;
import com.snopko.rest.logic.dto.UserDtoResponce;
import com.snopko.rest.logic.service.CoinLoreService;
import com.snopko.rest.logic.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/crypto")
public class CryptoWatcherController {
    @Autowired
    private CoinLoreService coinLoreService;
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping()
    public ResponseEntity<List<CryptoDto>> get() {
        return ResponseEntity.ok(coinLoreService.getAvailableCryptos());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CryptoInfoDto> get(@PathVariable("id") int id) {
        return ResponseEntity.ok(coinLoreService.getCurrentPrice(id));
    }

    @PostMapping(path = "/notify")
    public ResponseEntity<UserDtoResponce> subscribe(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userProfileService.add(dto));
    }
}
