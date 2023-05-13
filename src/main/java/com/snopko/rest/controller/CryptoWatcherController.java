package com.snopko.rest.controller;

import com.snopko.rest.logic.dto.CryptoDto;
import com.snopko.rest.logic.dto.CryptoInfoDto;
import com.snopko.rest.logic.dto.UserDto;
import com.snopko.rest.logic.dto.UserDtoResponce;
import com.snopko.rest.logic.service.CoinLoreService;
import com.snopko.rest.logic.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/crypto")
public class CryptoWatcherController {
    @Autowired
    private CoinLoreService coinLoreService;
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping()
    public CollectionModel<CryptoDto> get() {
        List<CryptoDto> cryptoDtos = coinLoreService.getAvailableCryptos();
        cryptoDtos.stream()
                .forEach(i -> i.add(linkTo(methodOn(CryptoWatcherController.class)
                        .get(i.getId()))
                        .withSelfRel()));
        return CollectionModel.of(cryptoDtos,
                linkTo(methodOn(CryptoWatcherController.class).get()).withSelfRel());
    }

    @GetMapping(path = "/{id}")
    public HttpEntity<CryptoInfoDto> get(@PathVariable("id") int id) {
        return ResponseEntity.ok(coinLoreService
                .getCurrentPrice(id)
                .add(linkTo(methodOn(CryptoWatcherController.class)
                        .get(id))
                        .withSelfRel())
                .add(linkTo(methodOn(CryptoWatcherController.class)
                        .get())
                        .withRel(IanaLinkRelations.COLLECTION)));
    }

    @PostMapping(path = "/notify")
    public HttpEntity<UserDtoResponce> subscribe(@RequestBody UserDto dto) {
        UserDtoResponce responce = userProfileService.add(dto);
        return ResponseEntity.ok(responce
                .add(linkTo(methodOn(CryptoWatcherController.class)
                        .get(responce.getIdCrypto()))
                        .withRel(IanaLinkRelations.ABOUT))
                .add(linkTo(methodOn(CryptoWatcherController.class)
                        .get())
                        .withRel(IanaLinkRelations.COLLECTION)));
    }
}
