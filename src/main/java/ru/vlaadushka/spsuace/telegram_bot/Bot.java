package ru.vlaadushka.spsuace.telegram_bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

    public class Bot extends AbilityBot {

    public static final String USERNAME = "@Marsichka_bot";
    public static final String TOKEN = System.getenv("VARIABLE_NAME");

        public Bot(DefaultBotOptions botOptions) {
        super(TOKEN, USERNAME, botOptions);
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("say hello world!")
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("Hello World!", ctx.chatId()))
                .post(ctx -> silent.send("Bye world", ctx.chatId()))
                .build();
    }

    @Override
    public int creatorId() {
        return 0;
    }

        @Override
    public String getBotToken() {
        return TOKEN;
    }
}
