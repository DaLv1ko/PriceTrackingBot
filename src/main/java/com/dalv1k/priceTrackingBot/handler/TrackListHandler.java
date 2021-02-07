package com.dalv1k.priceTrackingBot.handler;

import com.dalv1k.priceTrackingBot.bot.MyBot;
import com.dalv1k.priceTrackingBot.bot.Button;
import com.dalv1k.priceTrackingBot.entity.User;
import com.dalv1k.priceTrackingBot.repo.LinkRepo;
import com.dalv1k.priceTrackingBot.repo.UserRepo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TrackListHandler {

    public static void handle(Update update, UserRepo userRepo, MyBot myBot, LinkRepo linkRepo) throws TelegramApiException {

        SendMessage message = new SendMessage();
        User user = userRepo.getUserByChatId(update.getMessage().getChatId());

        switch (user.getBotState()) {
            case "TRACK_LIST": {
                if (update.getMessage().getText().contains("DELETE")) {
                    user.setDeleteTrackId(Integer.parseInt(update.getMessage().getText().replaceAll("[^0-9]", "")));
                    message = Button.getTwoButtons(update.getMessage().getChatId(),
                            "Ви впевнені?",
                            "Так",
                            "Ні");
                    user.setBotState("DELETE");
                    userRepo.save(user);
                }
                break;
            }
            case "DELETE": {
                //todo delete non exist element exception
                if (update.getMessage().getText().equals("Так")) {
                    DeleteMessage deleteMessage = new DeleteMessage();
                    deleteMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                    deleteMessage.setMessageId(linkRepo.getLinkById(
                            userRepo.getUserByChatId(
                                    update.getMessage().getChatId()).getDeleteTrackId()).getMessageId());
                    myBot.execute(deleteMessage);
                    linkRepo.deleteById(userRepo.getUserByChatId(update.getMessage().getChatId()).getDeleteTrackId());

                    message = Button.getOneButton(update.getMessage().getChatId(),
                            "Видалено успішно. " +
                                    "Ви можете видалити ще або повернутись до головного меню.",
                            "До головного меню");
                    user.setBotState("TRACK_LIST");
                    userRepo.save(user);

                }
                if (update.getMessage().getText().equals("Ні")) {
                    message = Button.getOneButton(update.getMessage().getChatId(),
                            "Видалення скасовано." +
                                    " Ви можете вибрати інший товар для видалення або повернутись до головного меню.",
                            "До головного меню");
                }
                break;
            }
        }
        if (update.getMessage().getText().equals("До головного меню")) {
            user.setBotState("MENU");
            userRepo.save(user);
            message = Button.getThreeButtons(update.getMessage().getChatId(),
                    "Використайте меню",
                    "Відслідкувати",
                    "Мої відстеження",
                    "Допомога");
        }

        myBot.execute(message);
    }
}
