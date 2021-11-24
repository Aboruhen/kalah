package com.start.kalah.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationKalahMetrics {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationKalahMetrics.class, args);
    }

}
