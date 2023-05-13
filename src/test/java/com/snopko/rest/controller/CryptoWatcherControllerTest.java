package com.snopko.rest.controller;

import com.snopko.rest.dao.repository.IUserProfileRepository;
import com.snopko.rest.logic.dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CryptoWatcherControllerTest {
    @Autowired
    private IUserProfileRepository repository;
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    @Value("${crypto.list[0].symbol}")
    private String symbol;
    @Value("${crypto.list[0].id}")
    private int id;
    private long idUser;

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @DisplayName("get all crypto currency")
    public void get_all() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/crypto/")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @DisplayName("get crypto currency by valid id")
    public void get_by_id_ok() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/crypto/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @DisplayName("get crypto currency by invalid id")
    public void get_by_id_400() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/crypto/5")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Order(4)
    @DisplayName("notify")
    public void notify_ok() {
        UserDto userDto = new UserDto("vova", symbol);
        ValidatableResponse vr = RestAssured.given()
                .with()
                .body(userDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/v1/crypto/notify")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
        idUser = Long.parseLong(vr.extract().path("id").toString());
        long idCrypto = Long.parseLong(vr.extract().path("idCrypto").toString());

        Assertions.assertEquals(idCrypto, this.id);
    }

    @Test
    @Order(5)
    @DisplayName("notify 400")
    public void notify_400() {
        UserDto userDto = new UserDto("vova", "error");
        ValidatableResponse vr = RestAssured.given()
                .with()
                .body(userDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/v1/crypto/notify")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400);
    }

    @AfterAll
    public void destroy() {
        repository.deleteById(idUser);
    }
}

