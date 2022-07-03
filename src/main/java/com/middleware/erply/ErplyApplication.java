package com.middleware.erply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.middleware.erply.properties.AuthProperties;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties({
        AuthProperties.class,
})
public class ErplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErplyApplication.class, args);
	}

}
