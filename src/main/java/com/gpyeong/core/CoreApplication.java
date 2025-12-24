package com.gpyeong.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.data.mongodb.config.EnableMongoAuditing;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoAuditing
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
