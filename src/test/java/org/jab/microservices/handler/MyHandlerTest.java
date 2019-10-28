package org.jab.microservices.handler;

import org.jab.microservices.config.WebConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest({MyHandler.class, WebConfig.class})
@DirtiesContext
public class MyHandlerTest {

    @Autowired
    private MyHandler myHandler;

    @Disabled
    @Test
    public void Given_a_request_When_check_version_in_parallel_And_both_version_are_Ok_Then_returns_true() {


        //Mono<ServerResponse> result = myHandler.getEndpoint();

        StepVerifier.create(null)
                //.expectNext(true)
                .expectComplete()
                .verify();
    }
}