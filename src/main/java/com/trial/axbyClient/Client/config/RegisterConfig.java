package com.trial.axbyClient.Client.config;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RegisterConfig {


    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

}