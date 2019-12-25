package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.nodes.Document;


public class Book {

    public Book(){
    }

    private String titles;
    private String likes;
    private String description;
    private String geners;
    private String coment;
    private String url;
    private String autorName;

    public String getTitle() {
        return titles;
    }
    public void setTitle(String titles) {
        this.titles = titles;;
    }

    public String getLikes() {
        return likes;
    }
    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeners() {
        return geners;
    }
    public void setGeners(String geners) {
        this.geners = geners;
    }

    public String getComment() {
        return coment;
    }
    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAutorName() {
        return autorName;
    }
    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

}