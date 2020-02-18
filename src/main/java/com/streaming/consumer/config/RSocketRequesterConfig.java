package com.streaming.consumer.config;

import io.rsocket.exceptions.RejectedResumeException;
import io.rsocket.resume.PeriodicResumeStrategy;
import io.rsocket.transport.netty.client.TcpClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class RSocketRequesterConfig implements Supplier<RSocketRequester> {

    private static final AtomicReference<RSocketRequester> R_SOCKET_REQUESTER = new AtomicReference<>();

    @Autowired
    private RSocketRequester.Builder rSocketRequesterBuilder;

    @PostConstruct
    void init() {
        final InetSocketAddress address = new InetSocketAddress(7000);
        rSocketRequesterBuilder
                .rsocketFactory(rsocketFactory -> rsocketFactory
                        .errorConsumer(error -> {
                            if (error instanceof RejectedResumeException) {
                                log.error("Server error:" + error.getMessage());
                                init();
                            }
                        })
                        .resume()
                        .resumeStreamTimeout(Duration.ofSeconds(1))
                        .resumeStrategy(() -> new PeriodicResumeStrategy(Duration.ofSeconds(1))))
                .connect(TcpClientTransport.create(address))
                .retryBackoff(Integer.MAX_VALUE, Duration.ofSeconds(1))
                .subscribe(R_SOCKET_REQUESTER::set);
    }

    @Override
    public RSocketRequester get() {
        return R_SOCKET_REQUESTER.get();
    }
}
