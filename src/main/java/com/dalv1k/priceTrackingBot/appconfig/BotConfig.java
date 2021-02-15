package com.dalv1k.priceTrackingBot.appconfig;

import com.dalv1k.priceTrackingBot.bot.MyBot;
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

}
