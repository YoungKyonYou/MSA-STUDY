package com.youyk.userservice.client;

import com.youyk.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//우리가 호출할 micro service 이름 명시
@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @GetMapping("/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable("userId") String userId);
}
