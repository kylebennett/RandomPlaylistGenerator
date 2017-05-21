package com.kylebennett.randomplaylistgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class RandomplaylistgeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandomplaylistgeneratorApplication.class, args);
    }
}
