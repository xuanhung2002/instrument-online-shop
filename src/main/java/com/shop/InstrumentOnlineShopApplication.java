package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.shop.entity"})
//@ComponentScan(basePackages = {"com.shop.service", "com.shop.service.impl"})
public class InstrumentOnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstrumentOnlineShopApplication.class, args);
	}

}
