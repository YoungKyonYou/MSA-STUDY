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
        //flatMapMany: 이 연산자는 Mono 또는 Flux를 반환하는 함수를 인자로 받아서, 이 함수를 각 아이템에 적용한 후, 반환된 모든 스트림을 하나의 Flux로 병합합니다
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
