package com.dalv1k.priceTrackingBot.handler;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.bot.Button;
import com.dalv1k.priceTrackingBot.entity.User;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MainHandler {

    private UserRepo userRepo;
    private LinkRepo linkRepo;

    public MainHandler(UserRepo userRepo, LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
    }

    public void handleUpdate(Update update, MyBot myBot) throws TelegramApiException {

        try {
            if (update.getMessage().getText().equals("/start")) {
                startHandler(update, myBot);
                SendMessage message = Button.getThreeButtons(update.getMessage().getChatId(), "Скористайся меню",
                        "Відслідкувати",
                        "Мої відстеження",
                        "Допомога");
                myBot.execute(message);
            } else {
                stateHandler(update, myBot);
            }
        } catch (NullPointerException e) {
            System.out.println("MainHandler: "+e);
            SendMessage message = new SendMessage();
            message.setText("Пришліть будь ласка текстове повідомлення.");
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            myBot.execute(message);
        }

    }

    public void stateHandler(Update update, MyBot myBot) throws TelegramApiException{
        User user = userRepo.getUserByChatId(update.getMessage().getChatId());
        switch (user.getBotState()) {

            case "MENU":
                MenuHandler.handle(update, myBot, userRepo, linkRepo);
                break;

            case "ACCEPT":
            case "ERROR":
            case "PRICE_CHECK":
            case "ADD_TRACK":
                AddTrackHandler.handle(update, myBot, userRepo, linkRepo);
                break;

            case "DELETE":
            case "TRACK_LIST":
                TrackListHandler.handle(update, userRepo, myBot, linkRepo);
                break;
        }

    }

    public void startHandler(Update update, MyBot myBot) throws TelegramApiException {

        User user;
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        try {
            user = userRepo.getUserByChatId(update.getMessage().getChatId());
            user.setBotState("MENU");
            userRepo.save(user);
            message.setText("Ми раді що ви повернулися!");
        } catch (NullPointerException e) {
                user = new User();
                user.setChatId(update.getMessage().getChatId());
                user.setBotState("MENU");
                userRepo.save(user);
                message.setText("Ти успішно зареєстрований");
        }
        myBot.execute(message);
    }

}
