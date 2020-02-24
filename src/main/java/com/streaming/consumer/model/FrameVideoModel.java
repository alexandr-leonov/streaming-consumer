package com.streaming.consumer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class FrameVideoModel {

    private final Long chunkCounter;

    private final byte[] content;

}
