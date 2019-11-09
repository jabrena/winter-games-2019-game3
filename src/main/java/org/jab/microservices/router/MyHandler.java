package org.jab.microservices.router;

import io.vavr.control.Try;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.BaseStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
public class MyHandler {

    private Flux<String> fromPath(Path path) {
        return Flux.using(() -> Files.lines(path),
                Flux::fromStream,
                BaseStream::close
        );
    }

    private Flux<String> getGods() {
        return Try.of(() -> {
            URL res = getClass().getClassLoader().getResource("greek.json");
            Path path = Paths.get(res.toURI());
            return fromPath(path);
        })
        .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage(), ex))
        .getOrElse(Flux.empty());
    }

    public Mono<ServerResponse> getEndpoint(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(getGods(), String.class);
    }

    public Mono<ServerResponse> getLevel1(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(Mono.just(new MyResponse2(new ArrayList<String>())), MyResponse2.class);
    }

    public Mono<ServerResponse> getLevel2(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(Mono.just(new MyResponse(Boolean.TRUE)), MyResponse.class);
    }
}