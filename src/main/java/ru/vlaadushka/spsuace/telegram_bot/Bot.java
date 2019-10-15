package ru.vlaadushka.spsuace.telegram_bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public static final String USERNAME = "@Marsichka_bot";
    public static final String TOKEN = "704406634:AAHypsnTvkeBUx3Lx87kMwVFYSjX0CwBHhQ";

    public Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Носик").setSwitchInlineQueryCurrentChat("Котик \"Носик\" любопытный"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Хвост").setUrl("https://vk.com/avershelp"));
        rowList.add(keyboardButtonsRow1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Лапка").setSwitchInlineQuery("Котик \"Лапка\" пушистая"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Усы").setCallbackData("Котик \"Усы"));
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Что выберешь ты?").setReplyMarkup(inlineKeyboardMarkup);
    }



    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage(message.getChatId(), message.getText()).setChatId(update.getMessage().getChatId());



        if(update.getMessage().getText().equals("Котик")){
            try {
                execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            if (update.getMessage().getText().equals("/hello")) {
                sendMessage.setText(String.valueOf(new HelloCommand()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().equals("Привет")) {
                sendMessage.setText("Приветствую тебя, человек!");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                    sendMessage.setText("Напиши мне 'Привет', человек, и я может быть отвечу тебе!");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

}
