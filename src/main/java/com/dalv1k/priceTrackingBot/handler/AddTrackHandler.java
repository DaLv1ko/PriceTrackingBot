package com.dalv1k.priceTrackingBot.handler;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.bot.Button;
import com.dalv1k.priceTrackingBot.util.Parser;
import com.dalv1k.priceTrackingBot.entity.Link;
import com.dalv1k.priceTrackingBot.entity.User;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class AddTrackHandler {

    public static void handle(Update update, MyBot myBot, UserRepo userRepo, LinkRepo linkRepo) throws TelegramApiException {

        User user = userRepo.getUserByChatId(update.getMessage().getChatId());
        SendMessage message = new SendMessage();

        switch (user.getBotState()) {
            case "ACCEPT": {
                System.out.println("accept");
                if (update.getMessage().getText().equals("Додати ще")) {
                    message = Button.getOneButton(update.getMessage().getChatId(), "Надішліть мені повне посилання на товар", "До головного меню");
                    user.setBotState("ADD_TRACK");
                    userRepo.save(user);
                }
                break;
            }
            case "PRICE_CHECK": {
                System.out.println("price_check");
                if (update.getMessage().getText().contains("Підтвердити")) {
                    user.setBotState("ACCEPT");
                    userRepo.save(user);
                    message = Button.getTwoButtons(update.getMessage().getChatId(), "Збережено.",
                            "До головного меню", "Додати ще");
                }
                if (update.getMessage().getText().contains("Інша ціна")) {
                    SendMessage messageAdmin = new SendMessage();
                    messageAdmin.setChatId("192496395");

                    user.setBotState("ERROR");
                    userRepo.save(user);
                    List<Link> links = linkRepo.getLinkByUserId(user.getId());
                    int maxId = 0;
                    Link errorLink = new Link();
                    for (Link l : links
                    ) {
                        if(l.getId()>maxId) {
                            maxId = l.getId();
                            errorLink = l;
                        }
                    }
                    messageAdmin.setText("Помилка в користувача. \n"+
                            update.getMessage().getFrom()+"\n"+
                            "Link: "+errorLink.getLink()+"\n"+
                            "Price: "+errorLink.getPrice());
                    linkRepo.deleteById(maxId);
                    myBot.execute(messageAdmin);
                    message = Button.getOneButton(update.getMessage().getChatId(), "Ми повідомили про помилку адміністратора", "До головного меню");
                }
                break;
            }
            case "ADD_TRACK": {
                System.out.println("add_track");
                if (update.getMessage().getText().equals("Повідомити адміністратора")) {

                    message = Button.getOneButton(update.getMessage().getChatId(), "Дякуємо! Адміністратор зв'яжеться з вами",
                            "До головного меню");
                    SendMessage messageAdmin = new SendMessage();
                    messageAdmin.setChatId("192496395");

                    messageAdmin.setText("Помилка в користувача. \n"+
                            update.getMessage().getFrom()+"\n"+
                            "Link: "+user.getWrongLink());

                    myBot.execute(messageAdmin);

                } else if (update.getMessage().getText().contains("mediaexpert.pl")) {
                    Link link = new Link();
                    link.setLink(update.getMessage().getText());
                    if (Parser.parseMediaExpert(update.getMessage().getText()) != 0) {
                        link.setPrice(Parser.parseMediaExpert(update.getMessage().getText()));
                        link.setUserId(user.getId());
                        linkRepo.save(link);
                        user.setBotState("PRICE_CHECK");
                        userRepo.save(user);
                        message = Button.getTwoButtons(update.getMessage().getChatId(), "Ціна на даний момент: " + link.getPrice() + ".",
                                "Підтвердити", "Інша ціна");
                    } else {

                        message = Button.getTwoButtons(update.getMessage().getChatId(),
                                "Ви ввели некоректне посилання. Перевірте будь ласка та надішліть ще раз. Також Ви можете повідомити про це адміністратора.",
                                "До головного меню",
                                "Повідомити адміністратора");
                    }
                } else if (update.getMessage().getText().contains("euro.com.pl")) {
                    Link link = new Link();
                    link.setLink(update.getMessage().getText());
                    if (Parser.parseEuro(update.getMessage().getText()) != 0) {
                        link.setPrice(Parser.parseEuro(update.getMessage().getText()));
                        link.setUserId(user.getId());
                        linkRepo.save(link);
                        user.setBotState("PRICE_CHECK");
                        userRepo.save(user);
                        message = Button.getTwoButtons(update.getMessage().getChatId(), "Ціна на даний момент: " + link.getPrice() + ".",
                                "Підтвердити", "Інша ціна");
                    } else {

                        message = Button.getTwoButtons(update.getMessage().getChatId(),
                                "Ви ввели некоректне посилання. Перевірте будь ласка та надішліть ще раз. Також Ви можете повідомити про це адміністратора.",
                                "До головного меню",
                                "Повідомити адміністратора");
                    }
                } else {
                    user.setWrongLink(update.getMessage().getText());
                    userRepo.save(user);
                    message = Button.getTwoButtons(update.getMessage().getChatId(),
                            "Ви ввели некоректне посилання. Перевірте будь ласка та надішліть ще раз. Також Ви можете повідомити про це адміністратора",
                            "До головного меню",
                            "Повідомити адміністратора");

                }
                break;
            }
        }
        if (update.getMessage().getText().equals("До головного меню")) {
            user.setBotState("MENU");
            userRepo.save(user);
            message = Button.getThreeButtons(update.getMessage().getChatId(), "Використай меню",
                    "Відслідкувати",
                    "Мої відстеження",
                    "Допомога");
        }


        myBot.execute(message);

    }
}
