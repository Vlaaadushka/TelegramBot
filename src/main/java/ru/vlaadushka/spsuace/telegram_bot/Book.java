package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;

public class Book {
    private Document document;

    private Pars pars = new Pars();

    public Book(){
        connect();
    }

    private void connect(){
        try {
            document = Jsoup.connect("https://www.surgebook.com/GGhe4ka/book/devushka-s-rozovymi-volosami").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle(){
        return document.title();
    }

    public String getLikes(){
        return pars.getString(document, "likes", false);
//        Element element = document.getElementById("likes");
//        return element.text();
    }

    public String getDescription(){
        return pars.getString(document, "description", false);
//        Element element = document.getElementById("description");
//        return element.text();
    }

    public String getGeners(){
        return pars.getString(document, "genres d-block", true);
//        Elements elements = document.getElementsByClass("genres d-block");
//        return elements.text();
    }

    public String getCommentList(){
//        pars.getString(document, "comment_mv1_item", true);
        Elements elements = document.getElementsByClass("comment_mv1_item");
        String coment = elements.text();
//        String coment = pars.getString(document, "comment_mv1_item", true);
        coment = coment.replaceAll("Ответить", "\n\n");
        coment = coment.replaceAll("Нравится", "");
        coment = coment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        coment = coment.replaceAll("\\d{2}:\\d{2}:\\d{2}", "");
        return coment;
    }

    public String getImg(){
        String url = pars.getElementAttr(document, "cover-book", "style");
//       Elements elements = document.getElementsByClass("cover-book");
//        String url = elements.attr("style");
//        String url = pars.getString(document, "style", true);
        url = url.replace("background-image: url('", "");
        url = url.replace("');", "");
        return url;
    }

    public String getAutorName(){
        return pars.getString(document, "text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis", true);
//        Elements elements = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
//        return elements.text();
    }

}