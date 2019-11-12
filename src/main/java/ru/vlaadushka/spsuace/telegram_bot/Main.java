package ru.vlaadushka.spsuace.telegram_bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    public  static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            botOptions.setProxyHost("177.8.226.254");
            botOptions.setProxyPort(8080);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
            telegramBotsApi.registerBot(new Bot(botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}