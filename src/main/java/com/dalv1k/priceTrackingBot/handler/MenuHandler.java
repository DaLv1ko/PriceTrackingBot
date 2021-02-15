package com.dalv1k.priceTrackingBot.handler;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.bot.Button;
import com.dalv1k.priceTrackingBot.bot.Test;
import com.dalv1k.priceTrackingBot.entity.Link;
import com.dalv1k.priceTrackingBot.entity.User;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MenuHandler {


    public static void handle(Update update, MyBot myBot, UserRepo userRepo, LinkRepo linkRepo) throws TelegramApiException {
        SendMessage message = new SendMessage();
        User user = userRepo.getUserByChatId(update.getMessage().getChatId());

        switch (update.getMessage().getText()) {
            case "Відслідкувати": {
                user.setBotState("ADD_TRACK");
                message = Button.getOneButton(update.getMessage().getChatId(),
                        "Надішліть мені повне посилання на товар",
                        "До головного меню");
                myBot.execute(message);
                break;
            }
            case "Мої відстеження": {
                user.setBotState("TRACK_LIST");
                SendMessage trackMessage = new SendMessage();
                List<Link> links = linkRepo.getLinkByUserId(user.getId());
                if (links.isEmpty()) {
                    trackMessage.setText("Ваш список відстежень пустий");
                    trackMessage.setChatId(String.valueOf(user.getChatId()));
                    user.setBotState("MENU");
                    myBot.execute(trackMessage);

                } else {
                    trackMessage.setChatId(String.valueOf(user.getChatId()));
                    int messageId = update.getMessage().getMessageId();
                    for (Link link : links) {
                        trackMessage.setText(link.getLink() + "\n" +
                                "Ціна: " + link.getPrice() + "\n" +
                                "/DELETE_" + link.getId() + "\n"
                        );
                        link.setMessageId(++messageId);
                        myBot.execute(trackMessage);
                    }
                    message = Button.getOneButton(update.getMessage().getChatId(),
                            "Ти можеш видалити відстеження." +
                                    " Для цього натисни DELETE під посиланням на товар",
                            "До головного меню");
                    myBot.execute(message);
                }

                break;
            }
            case "Допомога": {
                message.setText("1. Ми працюємо лише з сайтом mediaexpert.pl . \n" +
                        "2. Якщо ти хочеш відслідковувати ціну на іншому сайті звернись до @dalv1k \n");
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                myBot.execute(message);
                break;
            }
            case "admin": {
                if (update.getMessage().getChatId()==192496395){
                    SendMessage adminMessage = new SendMessage();
                    SendMessage adminMessage2 = new SendMessage();
                    Test test = new Test();
//
                    adminMessage.setText("123");
                    adminMessage2.setText(test.getName());
                    adminMessage.setChatId("192496395");
                    adminMessage2.setChatId("192496395");
                    myBot.execute(adminMessage);
                    myBot.execute(adminMessage2);
                }

                break;
            }
            default: {
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText("Використайте меню");
            }

        }
        userRepo.save(user);
    }

}
