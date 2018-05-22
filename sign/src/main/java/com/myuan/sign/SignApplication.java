package com.myuan.sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.myuan.sign.dao"})	//扫描repository
@EntityScan(basePackages = {"com.myuan.sign.entity"})	//扫描entity实体
@EnableTransactionManagement    //开启事务
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class SignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SignApplication.class, args);
	}
}
