package com.clean.cleanroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CleanRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanRoomApplication.class, args);
    }
}
