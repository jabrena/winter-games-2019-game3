package org.jab.microservices.router;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

public class MyRouter {

    public RouterFunction<ServerResponse> myRoutes(MyHandler myHandler) {

        return RouterFunctions
                .route(GET("/api/endpoint")
                        .and(accept(APPLICATION_JSON)), myHandler::getEndpoint)
                .andRoute(GET("/api/endpoint/level1")
                        .and(accept(APPLICATION_JSON)), myHandler::getLevel1)
                .andRoute(GET("/api/endpoint/level1/level2")
                        .and(accept(APPLICATION_JSON)), myHandler::getLevel2)
                .andRoute(GET("/api/endpoint/boom")
                    .and(accept(APPLICATION_JSON)), myHandler::getBoom);
    }
}
