package org.jab.microservices.router;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jab.microservices.config.SecurityConfig;
import org.jab.microservices.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.BDDAssertions.then;
import static org.jab.microservices.config.AuthFilter.KEY_HEADER_1;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebFluxTest({MyRouter.class, WebConfig.class, SecurityConfig.class})
public class MyRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    Function<String, List<String>> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
        return deserializedData;
    }).getOrElseThrow(ex -> {
        LOGGER.error("Bad Serialization process", ex);
        throw new RuntimeException(ex);
    });

    private String generateToken(int length) {

        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }

    @Test
    public void given_MyRouter_when_callEndpoint_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint")
                .header(KEY_HEADER_1, generateToken(100))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(myResponse -> {
                    then(serialize.apply(myResponse))
                            .isEqualTo(List.of("Zeus",  "Hera",  "Poseidon",  "Demeter",  "Ares",  "Athena",  "Apollo",  "Artemis",  "Hephaestus",  "Aphrodite",  "Hermes",  "Dionysus",  "Hades",  "Hypnos",  "Nike",  "Janus",  "Nemesis",  "Iris",  "Hecate",  "Tyche"));
                });
    }

    @Test
    public void given_MyRouter_when_callEndpointWithoutHeader_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void given_MyRouter_when_callBadHeader_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint")
                .header(KEY_HEADER_1, generateToken(99))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void given_MyRouter_when_callLevel1_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint/level1")
                .header(KEY_HEADER_1, generateToken(100))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MyResponse2Test.class)
                .value(myResponse -> {
                            then(myResponse)
                            .isEqualTo(new MyResponse2Test(List.of("Zeus",  "Hera",  "Poseidon",  "Demeter",  "Ares",  "Athena",  "Apollo",  "Artemis",  "Hephaestus",  "Aphrodite",  "Hermes",  "Dionysus",  "Hades",  "Hypnos",  "Nike",  "Janus",  "Nemesis",  "Iris",  "Hecate",  "Tyche")));
                        }
                );
    }

    @Test
    public void given_MyRouter_when_callLevel2_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint/level1/level2")
                .header(KEY_HEADER_1, generateToken(100))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MyResponse.class)
                .value(myResponse -> {
                            then(myResponse.isStatus()).isEqualTo(true);
                        }
                );
    }

    @Test
    public void given_MyRouter_when_callBadRequest_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpointX")
                .header(KEY_HEADER_1, generateToken(100))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void given_MyRouter_when_triggerException_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint/boom")
                .header(KEY_HEADER_1, generateToken(100))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

}