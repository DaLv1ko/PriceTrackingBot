package com.dalv1k.priceTrackingBot.bot;

import com.dalv1k.priceTrackingBot.handler.MainHandler;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import com.dalv1k.priceTrackingBot.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramWebhookBot {

    @Value("${botUserName}")
    private String botUserName;

    @Value("${webHookPath}")
    private String webHookPath;

    @Value("${botToken}")
    private String botToken;

    @Autowired
    private LinkRepo linkRepo;
    @Autowired
    private UserRepo userRepo;


    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            MainHandler mainHandler = new MainHandler(userRepo,linkRepo);
            mainHandler.handleUpdate(update,this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Scheduled(fixedRate = 1000*60*60*8)
    public void check() throws TelegramApiException {
        Checker.checkPrices(linkRepo,userRepo,this);
        SendMessage message = new SendMessage();
        message.setChatId("192496395");
        message.setText("Зробив перевірку. Все ок.");
        execute(message);
    }


    @Scheduled(fixedDelay=900000)
    public void herokuNotIdle(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://price-tracking-bot.herokuapp.com/", Object.class);
        } catch (HttpClientErrorException e){
            System.out.println("NotIdleMethod");
        }
    }


}
