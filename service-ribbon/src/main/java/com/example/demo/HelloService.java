package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.ribbon.proxy.annotation.Hystrix;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    //@HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://service-a/hi?name=" + name, String.class);
    }


    public String hiError(String name) {
        return "hi," + name + ",sorry,error!";
    }


}
