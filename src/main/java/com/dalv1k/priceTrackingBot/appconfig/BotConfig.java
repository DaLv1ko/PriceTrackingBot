package com.dalv1k.priceTrackingBot.appconfig;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public MyBot myBot(){
        MyBot myTestBot = new MyBot();
        myTestBot.setBotUserName(botUserName);
        myTestBot.setWebHook(webHookPath);
        myTestBot.setBotToken(botToken);
        return myTestBot;
    }



}
