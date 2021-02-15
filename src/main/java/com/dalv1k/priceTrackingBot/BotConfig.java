package com.dalv1k.priceTrackingBot;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.bot.Test;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

@Setter
@Getter
@Configuration
@ComponentScan("com.dalv1k.priceTrackingBot")
@PropertySource("classpath:application.properties")
public class BotConfig {


//    @Bean
//    public MyBot myBot(){
//        MyBot myTestBot = new MyBot();
//        myTestBot.setBotUserName(botUserName);
//        myTestBot.setWebHook(webHookPath);
//        myTestBot.setBotToken(botToken);
//        return myTestBot;
//    }

//    @Bean
//    public Test test(){
//        Test test1 = new Test();
//        test1.setName(test);
//            return test1;
//    }



}
