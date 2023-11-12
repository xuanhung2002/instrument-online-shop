package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.shop.entity"})
public class InstrumentOnlineShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(InstrumentOnlineShopApplication.class, args);
	}

}
