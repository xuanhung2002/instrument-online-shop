package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.shop.entity"})
public class InstrumentOnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstrumentOnlineShopApplication.class, args);
	}

}
