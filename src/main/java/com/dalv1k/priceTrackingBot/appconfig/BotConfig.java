package com.dalv1k.priceTrackingBot.appconfig;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.bot.Test;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

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
    private String test;

    @Bean
    public MyBot myBot(){
        MyBot myTestBot = new MyBot();
        myTestBot.setBotUserName(botUserName);
        myTestBot.setWebHook(webHookPath);
        myTestBot.setBotToken(botToken);
        return myTestBot;
    }

    @Bean
    public Test testBean(){
        Test test1 = new Test();
        test1.setName(test);
            return test1;
    }



}
