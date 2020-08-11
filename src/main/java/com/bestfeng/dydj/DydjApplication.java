package com.bestfeng.dydj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DydjApplication {

	public static void main(String[] args) {
		SpringApplication.run(DydjApplication.class, args);
		log.info("SpringBoot DydjApplication start success");
	}

}
