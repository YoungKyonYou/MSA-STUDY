package com.youyk.orderservice.service.grpc;

import com.google.protobuf.Timestamp;
import com.google.type.Date;
import com.youyk.msa.OrderServiceGrpc;

import com.youyk.msa.ResponseOrder;
import com.youyk.msa.UserId;
import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.r2dbc.repository.OrderRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@GrpcService
public class OrderService extends OrderServiceGrpc.OrderServiceImplBase{
    private final OrderRepository orderRepository;

    @Override
    public void getOrder(UserId request, StreamObserver<ResponseOrder> responseObserver) {
        String userId = request.getUserId();
        orderRepository.findByUserId(userId)
                .flatMap(item -> {
                    ResponseOrder order = ResponseOrder.newBuilder()
                            .setCreatedAt(
                                    Date.newBuilder()
                                            .setYear(item.getCreatedAt().getYear())
                                            .setMonth(item.getCreatedAt().getMonthValue())
                                            .setDay(item.getCreatedAt().getDayOfMonth())
                                            .build()
                            )
                            .setProductId(item.getProductId())
                            .setQty(item.getQty())
                            .setTotalPrice(item.getTotalPrice())
                            .setUnitPrice(item.getUnitPrice())
                            .build();
                    return Flux.just(order);
                })
                .doOnNext(responseObserver::onNext)
                .doOnComplete(responseObserver::onCompleted)
                .doOnError(responseObserver::onError)
                .subscribe();
    }
}
