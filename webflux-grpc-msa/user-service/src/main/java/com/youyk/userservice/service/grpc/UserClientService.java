package com.youyk.userservice.service.grpc;

import com.youyk.msa.OrderServiceGrpc;
import com.youyk.msa.ReactorOrderServiceGrpc;
import com.youyk.msa.ResponseOrder;
import com.youyk.msa.UserId;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserClientService {

    private final ReactorOrderServiceGrpc.ReactorOrderServiceStub reactorOrderServiceStub;

    public Flux<ResponseOrder> getOrdersByUserId(String userId) {
        return reactorOrderServiceStub.getOrder(Mono.just(UserId.newBuilder().setUserId(userId).build()));
    }

}
