package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class Beans {

    @Bean
    public Random random() {
        return new Random();
    }

}
