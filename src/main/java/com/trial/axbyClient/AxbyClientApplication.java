package com.trial.axbyClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = {
		"com.trial.axbyClient"
})
@EnableFeignClients
public class AxbyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxbyClientApplication.class, args);
	}


}
