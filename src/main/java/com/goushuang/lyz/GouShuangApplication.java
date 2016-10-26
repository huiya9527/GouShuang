package com.goushuang.lyz;

import com.btmatthews.springboot.memcached.EnableMemcached;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableMemcached
public class GouShuangApplication {
	public static void main(String[] args) {
		SpringApplication.run(GouShuangApplication.class, args);
	}
}
