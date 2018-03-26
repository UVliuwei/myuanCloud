package com.myuan.answer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.myuan.answer.dao"})	//扫描repository
@EntityScan(basePackages = {"com.myuan.answer.entity"})	//扫描entity实体
@EnableTransactionManagement    //开启事务
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class AnswerApplication {

	public static void main(String[] args) {

	    SpringApplication.run(AnswerApplication.class, args);

	}
}
