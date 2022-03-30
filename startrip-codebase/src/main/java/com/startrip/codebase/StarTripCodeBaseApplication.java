package com.startrip.codebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"web"}) //web으로 지정 안해주면 오류 발생
public class StarTripCodeBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarTripCodeBaseApplication.class, args);
    }
}
