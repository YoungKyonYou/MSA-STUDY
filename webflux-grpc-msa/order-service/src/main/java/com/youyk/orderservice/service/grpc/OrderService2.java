package com.youyk.orderservice.service.grpc;

import com.google.type.Date;
import com.youyk.msa.OrderServiceGrpc;
import com.youyk.msa.ReactorOrderServiceGrpc;
import com.youyk.msa.ResponseOrder;
import com.youyk.msa.UserId;
import com.youyk.orderservice.r2dbc.repository.OrderRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@GrpcService
public class OrderService2 extends ReactorOrderServiceGrpc.OrderServiceImplBase {
    private final OrderRepository orderRepository;


    @Override
    public Mono<ResponseOrder> getOrder(Mono<UserId> request) {
       
    }
}
