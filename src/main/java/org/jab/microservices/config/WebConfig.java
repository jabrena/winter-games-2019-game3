package org.jab.microservices.config;

import org.jab.microservices.exception.GlobalErrorAttributes;
import org.jab.microservices.router.MyHandler;
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
    GlobalErrorAttributes globalErrorAttributes () {
        return new GlobalErrorAttributes();
    }

    @Bean
    MyHandler myHandler () {
        return new MyHandler();
    }

    @Bean
    public RouterFunction<ServerResponse> myRoutes (MyHandler myHandler) {
        return new MyRouter().myRoutes(myHandler);
    }
}