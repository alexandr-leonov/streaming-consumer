package com.streaming.consumer.controller;

import com.streaming.consumer.config.RSocketRequesterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FileController {

    @Autowired
    private RSocketRequesterConfig rSocketRequesterConfig;

    @GetMapping(value = "/file/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<byte[]> greet(@PathVariable String filename) {
        return rSocketRequesterConfig.get()
                .route("file")
                .metadata(filename, MimeTypeUtils.APPLICATION_OCTET_STREAM)
                .data(filename)
                .retrieveMono(byte[].class);
    }

    @GetMapping(value = "/cam/capture", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<byte[]> capture() {
        return rSocketRequesterConfig.get()
                .route("cam-capture")
                .retrieveMono(byte[].class);
    }

}
