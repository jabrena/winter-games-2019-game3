package org.jab.microservices.router;

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

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebFluxTest({MyRouter.class, SecurityTestConfiguration.class, WebConfig.class})
public class MyRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void given_MyRouter_when_callEndpoint_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint")
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
    public void given_MyRouter_when_callLevel1_then_expectedResult_Test() {

        webTestClient.get()
                .uri("/api/endpoint/level1")
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