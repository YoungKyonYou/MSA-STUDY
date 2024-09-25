package com.youyk.userservice.service.grpc;

import com.youyk.msa.OrderServiceGrpc;
import com.youyk.msa.ResponseOrder;
import com.youyk.msa.UserId;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserClientService {
    @GrpcClient("order-service")
    private OrderServiceGrpc.OrderServiceStub orderServiceStub;

    public Flux<ResponseOrder> getOrdersByUserId(String userId) {
        return Flux.create(sink -> {
            UserId request = UserId.newBuilder().setUserId(userId).build();
            orderServiceStub.getOrder(request, new StreamObserver<ResponseOrder>() {
                @Override
                public void onNext(ResponseOrder responseOrder) {
                    sink.next(responseOrder); // 데이터 방출
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t); // 에러 처리
                }

                @Override
                public void onCompleted() {
                    sink.complete(); // 완료 처리
                }
            });
        });
    }
}
