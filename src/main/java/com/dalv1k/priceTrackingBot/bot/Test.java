package com.dalv1k.priceTrackingBot.bot;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Setter
@PropertySource("classpath:application.properties")
public class Test {

    @Value("${testdalv1k.name}")
    private String name;

    public String getName() {
        return name;
    }
}
