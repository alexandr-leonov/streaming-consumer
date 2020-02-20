package com.streaming.consumer.controller;

import com.streaming.consumer.config.RSocketRequesterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FileController {

    @Autowired
    private RSocketRequesterConfig rSocketRequesterConfig;

    @GetMapping(value = "/file/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<byte[]> getFile(@PathVariable String filename) {
        return rSocketRequesterConfig.get()
                .route("file")
                .metadata(filename, MimeTypeUtils.APPLICATION_OCTET_STREAM)
                .data(filename)
                .retrieveMono(byte[].class);
    }

    @GetMapping(value = "/cam/{name}/capture", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<byte[]> capture(@PathVariable String name) {
        return rSocketRequesterConfig.get()
                .route("cam-capture")
                .metadata(name, MimeTypeUtils.IMAGE_JPEG)
                .data(name)
                .retrieveMono(byte[].class);
    }

    @GetMapping(value = "/play", produces = "video/webm")
    public Mono<byte[]> playMedia(@RequestParam("name") String filename) {
        return rSocketRequesterConfig.get()
                .route("play-file")
                .metadata(filename, MimeTypeUtils.APPLICATION_OCTET_STREAM)
                .data(filename)
                .retrieveMono(byte[].class);
    }

}
