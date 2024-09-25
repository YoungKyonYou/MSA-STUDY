package com.youyk.orderservice.service.grpc;

import com.google.protobuf.Timestamp;
import com.google.type.Date;
import com.youyk.msa.OrderServiceGrpc;

import com.youyk.msa.ReactorOrderServiceGrpc;
import com.youyk.msa.ResponseOrder;
import com.youyk.msa.UserId;
import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.r2dbc.repository.OrderRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@GrpcService
public class OrderService extends ReactorOrderServiceGrpc.OrderServiceImplBase{
    private final OrderRepository orderRepository;

    @Override
    public Flux<ResponseOrder> getOrder(Mono<UserId> request) {
        return request.flatMapMany(req -> orderRepository.findByUserId(req.getUserId())
                .map(item -> ResponseOrder.newBuilder()
                        .setCreatedAt(Date.newBuilder()
                                .setYear(item.getCreatedAt().getYear())
                                .setMonth(item.getCreatedAt().getMonthValue())
                                .setDay(item.getCreatedAt().getDayOfMonth())
                                .build())
                        .setProductId(item.getProductId())
                        .setQty(item.getQty())
                        .setTotalPrice(item.getTotalPrice())
                        .setUnitPrice(item.getUnitPrice())
                        .build()));
    }
}
