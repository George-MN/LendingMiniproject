package com.example.loanlending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Loanlending {

    public static void main(String[] args) {

        SpringApplication.run(Loanlending.class, args);
    }

}
