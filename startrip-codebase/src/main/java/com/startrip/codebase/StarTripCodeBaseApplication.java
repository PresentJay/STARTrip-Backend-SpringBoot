package com.startrip.codebase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class StarTripCodeBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarTripCodeBaseApplication.class, args);
    }
}

