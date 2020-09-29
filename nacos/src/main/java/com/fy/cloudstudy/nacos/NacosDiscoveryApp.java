package com.fy.cloudstudy.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosDiscoveryApp {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryApp.class, args);
    }
}
