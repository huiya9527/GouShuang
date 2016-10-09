package com.goushuang.lyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GouShuangApplication {
	public static void main(String[] args) {
		SpringApplication.run(GouShuangApplication.class, args);
	}
}
