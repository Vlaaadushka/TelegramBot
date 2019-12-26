package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.Jsoup;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot extends TelegramLongPollingBot {

    public static final String USERNAME = "@Marsichka_bot";
    public static final String TOKEN = System.getenv("VARIABLE_NAME");

    private Pars pars = new Pars();

    public Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        long chat_id = update.getMessage().getChatId();
        sendMessage.setText(teams(update.getMessage().getText(), chat_id));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String teams(String msg, long chat_id){
        if(msg.contains("Hi") || msg.contains("Hello") || msg.contains("Привет")){
            return "Привет дружище!";
        }
        if(msg.contains("/bookInformation")){
            msg = msg.replace("/bookInformation ", "");
            return getInfoBook(msg, chat_id);
        }
        if(msg.contains("/person")) {
            msg = msg.replace("/person ", "");
            return getInfoPerson(msg, chat_id);
        }

        return "Не понял!";
    }

    public String getInfoBook(String msg, long chat_id){
        Book book = null;
        try {
            book = pars.giveBook(Jsoup.connect("https://www.surgebook.com/GGhe4ka/book/" + msg).get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        SendPhoto sendPhotoRequest = new SendPhoto();
        try(InputStream in = new URL(book.getUrl()).openStream()){
            Files.copy(in, Paths.get("/Users/vlada/Desktop/srgBook"));  // Выгружаем изображение с интернета
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("/Users/vlada/Desktop/srgBook"));
            execute(sendPhotoRequest); // отправка картинки
            Files.delete(Paths.get("/Users/vlada/Desktop/srgBook")); // удаление картинки
        }
        catch (IOException ex){
            System.out.println("File not found");
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
        book.getUrl();

        String info = book.getTitle()
                + "\nАвтор " + book.getAutorName()
                + "\nЖанр " + book.getGeners()
                + "\n\nОписание\n" + book.getDescription()
                + "\n\nКоличество лайков " + book.getLikes()
                + "\n\nПоследние коментарии\n" + book.getComment();
        return info;
    }

    public String getInfoPerson(String msg, long chat_id){
        Autor autor = null;
        try {
            autor = pars.giveAutor(Jsoup.connect("https://www.surgebook.com/" + msg).get(), msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SendPhoto sendPhotoRequest = new SendPhoto();
        try(InputStream in = new URL(autor.getUrl()).openStream()){
            Files.copy(in, Paths.get("/Users/vlada/Desktop/srgAutor"));  // Выгружаем изображение с интернета
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("/Users/vlada/Desktop/srgAutor"));
            execute(sendPhotoRequest); // отправка картинки
            Files.delete(Paths.get("/Users/vlada/Desktop/srgAutor")); // удаление картинки
        }
        catch (IOException ex){
            System.out.println("File not found");
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
        autor.getUrl();

        return autor.getInfo();
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
