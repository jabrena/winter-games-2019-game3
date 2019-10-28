package org.jab.microservices.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ErrorHandler {

    public Mono<ServerResponse> notFund(final ServerRequest request) {
        return this.buildError(new Exception("Not fund."));
    }

    public Mono<ServerResponse> buildError(final Throwable throwable) {
        return Mono.just(throwable).transform(throwableMono ->
                throwableMono.flatMap(error ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(new ErrorResponse(throwable.getMessage())), ErrorResponse.class))
        );
    }

}