package org.jab.microservices.config;

import org.jab.microservices.handler.MyHandler;
import org.jab.microservices.router.MyRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@EnableWebFlux
@Configuration
public class WebConfig {

    @Bean
    MyHandler myHandler () {
        return new MyHandler();
    }

    @Bean
    public RouterFunction<ServerResponse> myRoutes (MyHandler myHandler) {
        return new MyRouter().myroutes(myHandler);
    }
}