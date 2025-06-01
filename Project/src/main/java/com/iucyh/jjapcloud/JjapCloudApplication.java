package com.iucyh.jjapcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({AppConfig.class, RepositoryConfig.class})
@SpringBootApplication(scanBasePackages = {"com.iucyh.jjapcloud.controller", "com.iucyh.jjapcloud.service", "com.iucyh.jjapcloud.common"})
public class JjapCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjapCloudApplication.class, args);
	}
}
