package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class FeignServiceHystrix implements FeignService{

    @Override
    public String sayHiFromClientOne(String name) {
        return "hystrix haha";
    }

}
