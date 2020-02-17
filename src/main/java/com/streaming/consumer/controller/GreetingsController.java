package com.streaming.consumer.controller;

import com.streaming.consumer.model.GreetingsRequest;
import com.streaming.consumer.model.GreetingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/*
 * Unfortunately functional endpoints not supported Rsocket yet :(
 *
 * Issue: https://github.com/spring-projects/spring-framework/issues/23135
 */
@RestController
public class GreetingsController {

    @Autowired
    private RSocketRequester requester;

    @GetMapping("/greet/{name}")
    public Mono<GreetingsResponse> greet(@PathVariable String name) {
        return requester
                .route("greet")
                .data(new GreetingsRequest(name))
                .retrieveMono(GreetingsResponse.class);
    }

}
