package com.eduardo.moneysaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneySaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneySaverApplication.class, args);
    }

}
