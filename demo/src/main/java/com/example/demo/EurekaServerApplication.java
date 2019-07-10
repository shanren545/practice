package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
//@EnableDiscoveryClient
public class EurekaServerApplication {

	public static void main(String[] args) {
	    //Object o = EurekaServerApplication::main;
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
