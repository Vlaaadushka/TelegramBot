package ru.vlaadushka.spsuace.telegram_bot;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Command extends TelegramLongPollingCommandBot {
    public static final String TOKEN = "704406634:AAHypsnTvkeBUx3Lx87kMwVFYSjX0CwBHhQ";

    public Command(String botUsername) {
        super(botUsername);
        register(new HelloCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId((update.getMessage().getChatId()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}