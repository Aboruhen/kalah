package com.start.game.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KalahApplication {

    public static void main(String[] args) {
        SpringApplication.run(KalahApplication.class, args);
    }

}
