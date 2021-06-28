package com.trial.axbyClient.Client.api;

import com.trial.axbyClient.Client.config.RegisterConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jplaceholder", url = "http://localhost:8080/", configuration = RegisterConfig.class)
public interface RegisterApi {


    @RequestMapping(method = RequestMethod.POST, value = "/registerPlayer/{name}")
    int registerPlayer(@PathVariable("name") String name);


}
