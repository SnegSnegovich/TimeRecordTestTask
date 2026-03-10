package com.leon.mikheevtesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MikheevTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MikheevTestTaskApplication.class, args);
    }

}
