package com.streaming.consumer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FrameVideoModel {

    private final byte[] content;

}
