package org.jab.microservices.router;

import org.jab.microservices.handler.MyResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class SystemTests {

    @Autowired
    private WebTestClient testClient;

    @Disabled
    @Test
    public void Given_a_request_When_both_cloudFoundry_installations_has_expected_versions_Then_return_true() throws Exception {
        testClient.get()
            .uri("/api/endpoint")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(MyResponse.class)
            .isEqualTo(new MyResponse(true));
    }
}
