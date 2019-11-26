package org.jab.microservices.config;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthFilter implements WebFilter {

    public final static String KEY_HEADER_1 = "X-HEADER1";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        Optional<List<String>> header1 = Optional.ofNullable(headers.get(KEY_HEADER_1));

        if(header1.isPresent()) {
            String headerValue = header1.get().get(0);
            LOGGER.info("Header {}: {}", KEY_HEADER_1, headerValue);

            //Simple verification
            if(headerValue.length() != 100) {
                LOGGER.error("Header {}: Bad value", KEY_HEADER_1);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            LOGGER.error("Header {} was not present in the request", KEY_HEADER_1);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

}