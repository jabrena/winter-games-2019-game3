package org.jab.microservices.router;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.BaseStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
public class MyHandler {

    private List<String> getGods2() {
        return Try.of(() -> {
            URL res = getClass().getClassLoader().getResource("greek.json");

            File file = new File(res.getFile());

            ObjectMapper objectMapper = new ObjectMapper();
            List<String> deserializedData = objectMapper.readValue(file, new TypeReference<List<String>>() {});

            return deserializedData;
        })
        .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage(), ex))
        .getOrElse(new ArrayList<>());
    }

    public Mono<ServerResponse> getEndpoint(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(Flux.fromIterable(getGods2()), ArrayList.class);
    }

    public Mono<ServerResponse> getLevel1(ServerRequest serverRequest) {

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(Mono.just(new MyResponse2(getGods2())), MyResponse2.class);
    }

    public Mono<ServerResponse> getLevel2(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(Mono.just(new MyResponse(Boolean.TRUE)), MyResponse.class);
    }
}