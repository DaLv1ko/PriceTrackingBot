package com.dalv1k.priceTrackingBot.util;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.entity.Link;
import com.dalv1k.priceTrackingBot.entity.User;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Checker {


    public static void checkPrices(LinkRepo linkRepo, UserRepo userRepo, MyBot myBot) throws TelegramApiException {
        List<Link> links = linkRepo.findAll();
        for (Link l : links) {
            int newPrice=l.getPrice();
            if (l.getLink().contains("mediaexpert.pl")) {
                newPrice = Parser.parseMediaExpert(l.getLink());
            }
            if (l.getLink().contains("euro.com.pl")) {
                newPrice = Parser.parseEuro(l.getLink());
            }
            if (newPrice != l.getPrice()) {
                User user = userRepo.getUserById(l.getUserId());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(user.getChatId()));
                sendMessage.setText("Нова ціна для:\n" + l.getLink() + "\nСтара ціна: " + l.getPrice() + "\nНова: " + newPrice);
                l.setPrice(newPrice);
                linkRepo.save(l);
                myBot.execute(sendMessage);
            }
        }
    }

}
