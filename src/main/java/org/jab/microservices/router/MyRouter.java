package org.jab.microservices.router;

import lombok.extern.slf4j.Slf4j;
import org.jab.microservices.handler.MyHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
public class MyRouter {

    public RouterFunction<ServerResponse> myroutes(MyHandler myHandler) {

        return route(
                GET("/api/endpoint").and(accept(APPLICATION_JSON)),
                    myHandler::getEndpoint).andRoute(
                GET("/api/endpoint/level1").and(accept(APPLICATION_JSON)),
                    myHandler::getLevel1).andRoute(
                GET("/api/endpoint/level1/level2").and(accept(APPLICATION_JSON)),
                    myHandler::getLevel2);
    }

}
