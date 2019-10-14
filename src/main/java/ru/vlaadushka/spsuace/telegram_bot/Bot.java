package ru.vlaadushka.spsuace.telegram_bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {

    public static final String USERNAME = "@Marsichka_bot";
    public static final String TOKEN = "704406634:AAHypsnTvkeBUx3Lx87kMwVFYSjX0CwBHhQ";

    public Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage(message.getChatId(), message.getText()).setChatId(update.getMessage().getChatId());

        if (update.getMessage().getText().equals("Привет")) {
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

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

}
