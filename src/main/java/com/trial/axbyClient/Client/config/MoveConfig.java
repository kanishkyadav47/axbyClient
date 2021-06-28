package com.trial.axbyClient.Client.config;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MoveConfig {


    @Bean
    public OkHttpClient client1() {
        return new OkHttpClient();
    }

}