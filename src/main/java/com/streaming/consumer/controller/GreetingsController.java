package com.streaming.consumer.controller;

import com.streaming.consumer.config.RSocketRequesterConfig;
import com.streaming.consumer.model.GreetingsRequest;
import com.streaming.consumer.model.GreetingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/*
 * Unfortunately functional endpoints not supported Rsocket yet :(
 *
 * Issue: https://github.com/spring-projects/spring-framework/issues/23135
 */
@RestController
public class GreetingsController {

    @Autowired
    private RSocketRequesterConfig rSocketRequesterConfig;

    @GetMapping("/greet/{name}")
    public Mono<GreetingsResponse> greet(@PathVariable String name) {
        return rSocketRequesterConfig.get()
                .route("greet")
                .data(new GreetingsRequest(name))
                .retrieveMono(GreetingsResponse.class);
    }

    @GetMapping(value = "/greet-stream/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GreetingsResponse> infiniteGreet(@PathVariable String name) {
        return rSocketRequesterConfig.get()
                .route("greet-stream")
                .data(new GreetingsRequest(name))
                .retrieveFlux(GreetingsResponse.class);
    }


}
