package com.friedrice.backendfriedrice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.friedrice.backendfriedrice.mapper")
@SpringBootApplication
public class BackendFriedRiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendFriedRiceApplication.class, args);
	}

}
