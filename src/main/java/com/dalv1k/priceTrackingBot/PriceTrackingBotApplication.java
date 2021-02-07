package com.dalv1k.priceTrackingBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceTrackingBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceTrackingBotApplication.class, args);
    }

}
