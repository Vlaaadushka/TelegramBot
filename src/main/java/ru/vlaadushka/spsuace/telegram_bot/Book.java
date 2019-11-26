package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;

public class Book {
    private Document document;

    public Book(){
        connect();
    }

    private void connect(){
        try {
            document = Jsoup.connect("https://www.surgebook.com/Lina_Kims/book/lazurit").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(){
        return document.title();
    }

    public String getLikes(){
        Element element = document.getElementById("likes");
        return element.text();
    }

    public String getDescription(){
        Element element = document.getElementById("description");
        return element.text();
    }

    public String getGeners(){
        Elements elements = document.getElementsByClass("genres d-block");
        return elements.text();
    }

    public String getCommentList(){
        Elements elements = document.getElementsByClass("comment_mv1_item");

        String coment = elements.text();
        coment = coment.replaceAll("Ответить", "\n\n");
        coment = coment.replaceAll("Нравится", "");
        coment = coment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        coment = coment.replaceAll("\\d{2}:\\d{2}:\\d{2}", "");
        return coment;
    }

    public String getImg(){
        Elements elements = document.getElementsByClass("cover-book");
        String url = elements.attr("style");
        url = url.replace("background-image: url('", "");
        url = url.replace("');", "");
        return url;
    }

    public String getAutorName(){
        Elements elements = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
        return elements.text();
    }

}