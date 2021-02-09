package com.dalv1k.priceTrackingBot.bot;

import com.dalv1k.priceTrackingBot.handler.MainHandler;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import com.dalv1k.priceTrackingBot.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MyBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
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
    }

    @Scheduled(fixedRate = 1000*60*60)
    public void live() throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId("192496395");
        message.setText("Я тут работаю. Все ок.");
        execute(message);
    }


    public void setWebHook(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}
