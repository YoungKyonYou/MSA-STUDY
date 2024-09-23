package com.youyk.springgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGatewayServiceApplication.class, args);
	}

	@Bean
	public HttpExchangeRepository httpTraceRepository() {
		return new InMemoryHttpExchangeRepository();
	}
}
