package ru.vlaadushka.spsuace.telegram_bot;

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

    public class Bot extends TelegramLongPollingCommandBot {

    public static final String USERNAME = "@Marsichka_bot";
    public static final String TOKEN = System.getenv("VARIABLE_NAME");

    public Bot(DefaultBotOptions botOptions) {
        super(botOptions, USERNAME);
        register(new HelloCommand());
    }


    private static SendMessage sendReplyKeyBoardMessage(long chatId) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("echo");
        keyboardFirstRow.add("double echo");
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Hello!");
        keyboardSecondRow.add('\u23F0' + "UNIX time:" + (System.currentTimeMillis() / 1000));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return new SendMessage().setChatId(chatId)
                .setText("Что выберешь ты?")
                .setReplyMarkup(replyKeyboardMarkup);
    }

   private SendMessage sendInlineKeyBoardMessage(long chatId) {
        
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton()
                .setText("Носик")
                .setSwitchInlineQueryCurrentChat("Котик \"Носик\" любопытный"));
        keyboardButtonsRow1.add(new InlineKeyboardButton()
                .setText("Хвост")
                .setUrl("https://vk.com/avershelp"));
        rowList.add(keyboardButtonsRow1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton()
                .setText("Лапка")
                .setSwitchInlineQuery("Котик \"Лапка\" пушистая"));
        keyboardButtonsRow2.add(new InlineKeyboardButton()
                .setText("Усы")
                .setCallbackData("Усы"));
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId)
                .setText("Что выберешь ты?")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        update.getUpdateId();



        if (update.hasMessage()) {
            SendMessage sendMessage = new SendMessage(message.getChatId(), message.getText())
                    .setChatId(update.getMessage().getChatId());
            SendMessage replyKeyboardMarkup = sendReplyKeyBoardMessage(update.getMessage().getChatId());
            SendMessage inlineKeyboardButton = sendInlineKeyBoardMessage(update.getMessage().getChatId());
            if (update.getMessage().getText().equals("Мур")) {
                try {
                    execute(replyKeyboardMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                if (message.getText().equals("hello")) {
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (update.getMessage().getText().equals("Котик")) {
                        try {
                            execute(inlineKeyboardButton);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
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
                }
            }
        } else if(update.hasCallbackQuery()) {
            String callbackId = update.getCallbackQuery().getId();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery()
                    .setCallbackQueryId(callbackId)
                    .setText("Окей").setShowAlert(true);
            try {
                execute(answerCallbackQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
