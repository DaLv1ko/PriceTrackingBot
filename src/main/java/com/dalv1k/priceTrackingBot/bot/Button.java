package com.dalv1k.priceTrackingBot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Button {



    public static SendMessage getOneButton(final long chatId, final String textMessage, String first) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(first));
        keyboard.add(row1);
        ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    public static SendMessage getTwoButtons(final long chatId, final String textMessage, String first, String second) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton(first));
        row2.add(new KeyboardButton(second));
        keyboard.add(row1);
        keyboard.add(row2);
        ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    public static SendMessage getThreeButtons(final long chatId, final String textMessage, String first, String second, String third) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton(first));
        row2.add(new KeyboardButton(second));
        row3.add(new KeyboardButton(third));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    private static ReplyKeyboardMarkup getReplyKeyboardMarkup(){
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    private static SendMessage createMessageWithKeyboard(final long chatId,
                                                         String textMessage,
                                                         final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}


