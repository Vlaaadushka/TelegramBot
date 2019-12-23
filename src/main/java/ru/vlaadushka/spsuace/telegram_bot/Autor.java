package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Autor {
    private Document document;
    private Document booksDoc;
    private String nameAutor = "";
    private Pars pars = new Pars();

    private int valuesLikesBooks;
    private int valuesViewsBooks;
    private int valuesCommentsBooks;

    public Autor(String name){
        nameAutor = name;
        connect();
    }

    private void connect(){
        try {
            document = Jsoup.connect("https://www.surgebook.com/" + nameAutor).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInfoPerson(){
        String info = "";

        info += "Имя " + getName() + "\n";
        info += "Статус " + getBio() + "\n";

        List<String> names = pars.getElementStrings(document, "info-stats-name");
        List<String> values = pars.getElementStrings(document, "info-stats-num");
        //Elements names = document.getElementsByClass("info-stats-name");
        //Elements values = document.getElementsByClass("info-stats-num");

        for(int i = 0; i < names.size(); i++)
            info += names.get(i) + ": " + values.get(i) + "\n";

        info += getBooks();

        return info;
    }


    public String getName(){
        return pars.getString(document, "author-name bold", true);
//        Elements namePerson = document.getElementsByClass("author-name bold");
//        return namePerson.text();
    }

    public String getBio(){
        return pars.getString(document, "author-bio", true);
//        Elements bioPerson = document.getElementsByClass("author-bio");
//        return bioPerson.text();
    }

    public String getImg(){
        String url = pars.getElementAttr(document, "user-avatar", "style");
//        Elements elements = document.getElementsByClass("user-avatar");
//        String url = elements.attr("style");
        url = url.replace("background-image: url('", "");
        url = url.replace("');", "");
        return url;
    }

    public String getBooks(){
        try {
            booksDoc = Jsoup.connect("https://surgebook.com/" + nameAutor + "/books/all").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text = "\nСписок книг: \n";
        ArrayList <String> textUrlBooks = new ArrayList<>();

        List<String> books = pars.getElementStrings(booksDoc, "book_view_mv1v2_title");
        List<String> booksUrl = pars.getElementsStrings(booksDoc, "book_view_mv1v2_title", "href");
//        Elements books = booksDoc.getElementsByClass("book_view_mv1v2_title");
//        Elements booksUrl = booksDoc.getElementsByClass("book_view_mv1v2_cover");

        for(int i = 0; i < books.size(); i++) {
            text += books.get(i) + "\n";
            textUrlBooks.add(booksUrl.get(i));
        }

        getStatistics(textUrlBooks);

        text += "\n\nКоличество лайков на книгах: " + valuesLikesBooks + "\n";
        text += "Количество просмотров на книгах: " + valuesViewsBooks + "\n";
        text += "Количество коментариев на книгах: " + valuesCommentsBooks + "\n";

        return text;
    }


    private String getStatistics(ArrayList list){
        for(int i = 0; i < list.size(); i++) {
            try {
                booksDoc = Jsoup.connect(list.get(i).toString()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> elements = pars.getElementStrings(booksDoc, "font-size-14 color-white ml-5");
//            Elements elements = booksDoc.getElementsByClass("font-size-14 color-white ml-5");
            valuesLikesBooks += Integer.valueOf(elements.get(0));
            valuesCommentsBooks += Integer.valueOf(elements.get(1));
            valuesViewsBooks += Integer.valueOf(elements.get(2));
        }
        return "";
    }
}
