package com.streaming.consumer.controller;

import com.streaming.consumer.config.RSocketRequesterConfig;
import com.streaming.consumer.model.FrameVideoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VideoController {

    @Autowired
    private RSocketRequesterConfig rSocketRequesterConfig;

    @GetMapping(value = "/cam/{name}/capture", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<byte[]> capture(@PathVariable String name) {
        return rSocketRequesterConfig.get()
                .route("cam-capture")
                .metadata(name, MimeTypeUtils.IMAGE_JPEG)
                .data(name)
                .retrieveMono(byte[].class);
    }

    //TODO add custom serializers/deserializers
    @GetMapping(value = "/cam/{name}")
    public Flux<FrameVideoModel> stream(@PathVariable String name) {
        return rSocketRequesterConfig.get()
                .route("cam-stream")
                .data(name)
                .retrieveFlux(FrameVideoModel.class);
    }


    @GetMapping(value = "/cam/exit")
    public Mono<Boolean> exitCamera() {
        return rSocketRequesterConfig.get()
                .route("cam-exit")
                .retrieveMono(Boolean.class);
    }
}
