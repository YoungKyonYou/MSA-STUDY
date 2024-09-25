package com.youyk.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * @LoadBalanced 어노테이션은 Spring Cloud에서 제공하는 기능으로, RestTemplate이나 WebClient를 사용하여 마이크로서비스 간의 통신을 할 때 로드 밸런싱 기능을 제공합니다.
	 * 이 어노테이션을 사용하면, 클라이언트 측에서 서비스 인스턴스를 호출할 때 여러 인스턴스 중 하나를 선택하는 로드 밸런싱 기능을 활성화할 수 있습니다. 이는 서비스의 가용성을 높이고, 트래픽을 고르게 분산시키는 데 도움이 됩니다.
	 *
	 * 이걸 쓰게 된 건 user-service-mvc.yml에서 order_service.url를 127.0.0.1:8000으로 설정했다가
	 * http://order-service/order-service로 바꿨기 때문에 쓰게 된다
	 *
	 * 마이크로서비스 환경에서는 여러 인스턴스의 마이크로서비스가 배포되어 있을 수 있습니다. 예를 들어, service-A가 service-B의 기능을 호출할 때 service-B의 여러 인스턴스 중 하나로 요청을 전달해야 합니다. 이때 로드 밸런싱이 필요합니다.
	 *
	 * @LoadBalanced를 사용하면 RestTemplate이 service-B의 모든 인스턴스 목록을 알고 있고, 요청을 분산시켜 각 인스턴스로 전달할 수 있게 됩니다.
	 *
	 * 이렇게 설정하고 나면, service-A에서 service-B를 호출할 때 직접 특정 URL을 지정하는 대신 서비스 이름으로 호출할 수 있습니다.
	 */
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

}
