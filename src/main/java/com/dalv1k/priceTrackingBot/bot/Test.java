package com.dalv1k.priceTrackingBot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "testdalv1k")
public class Test {

    private String name;

    public String getName(){
        return name;
    }

}
