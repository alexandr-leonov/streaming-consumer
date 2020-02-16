package com.streaming.consumer;

import lombok.Data;

import java.time.Instant;

@Data
class GreetingsResponse {

    private String greeting;

    GreetingsResponse() {
    }

    GreetingsResponse(String name) {
        this.withGreeting("Hello " + name + " @ " + Instant.now());
    }


    GreetingsResponse withGreeting(String message) {
        this.greeting = message;
        return this;
    }
}
