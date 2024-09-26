package com.youyk.userservice.service.grpc.config;

import com.youyk.msa.ReactorOrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    ManagedChannel channel;
    //@Value("${grpc-server.host}")
    private String hostName = "localhost";
    //@Value("${grpc-server.port}")
    private int port = 6561;

    @Bean
    public ReactorOrderServiceGrpc.ReactorOrderServiceStub getServiceStub(){
        this.channel = ManagedChannelBuilder
                .forAddress(hostName, port)
                .usePlaintext()
                .build();
        return ReactorOrderServiceGrpc.newReactorStub(channel);
    }

    @PreDestroy
    public void closeChannel() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
