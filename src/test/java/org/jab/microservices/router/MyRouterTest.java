package org.jab.microservices.router;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.jab.microservices.config.WebConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebFluxTest({MyRouter.class, SecurityTestConfiguration.class, WebConfig.class})
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

    @Test
    public void given_MyRouter_when_callEndpoint_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(myResponse -> {
                            then(serialize.apply(myResponse))
                                    .isEqualTo(List.of("Zeus",  "Hera",  "Poseidon",  "Demeter",  "Ares",  "Athena",  "Apollo",  "Artemis",  "Hephaestus",  "Aphrodite",  "Hermes",  "Dionysus",  "Hades",  "Hypnos",  "Nike",  "Janus",  "Nemesis",  "Iris",  "Hecate",  "Tyche"));
                });
    }

    @Disabled
    @Test
    public void given_MyRouter_when_callLevel1_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint/level1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MyResponse2.class)
                .value(myResponse -> {
                            then(myResponse).isEqualTo(new MyResponse2(new ArrayList<String>()));
                        }
                );
    }

    @Test
    public void given_MyRouter_when_callLevel2_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint/level1/level2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MyResponse.class)
                .value(myResponse -> {
                            then(myResponse.isStatus()).isEqualTo(true);
                        }
                );
    }
}