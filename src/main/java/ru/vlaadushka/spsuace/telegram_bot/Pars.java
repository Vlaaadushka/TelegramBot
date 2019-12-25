package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pars {

    public String getString(Document document, String name, boolean useElements) {
        if( !useElements) {
            Element element = document.getElementById(name);
            return element.text();
        } else {
            Elements elements = document.getElementsByClass(name);
            return elements.text();
        }
    }

    public List<String> getElementStrings(Document document, String name) {
        ArrayList<Element> elements = document.getElementsByClass(name);
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            strings.add(elements.get(i).text());
        }

        return strings;
    }

    public List<String> getElementsStrings(Document booksDoc, String name, String nameAttr) {
        ArrayList<Element> element = booksDoc.getElementsByClass(name);
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < element.size(); i++) {
            strings.add(element.get(i).attr(nameAttr));
        }

        return strings;
    }

    public String getElementAttr(Document document, String name, String nameAttr) {
        Elements elements = document.getElementsByClass(name);
        return elements.attr(nameAttr);
    }

    public Book giveBook(Document document) {

        String titles = document.title();
        String likes = getString(document, "likes", false);
        String description = getString(document, "description", false);
        String geners = getString(document, "genres d-block", true);
        String autorName = getString(document, "text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis", true);

        Book book = new Book();
        book.setTitle(titles);
        book.setLikes(likes);
        book.setDescription(description);
        book.setGeners(geners);
        book.setAutorName(autorName);

        String coment = getString(document, "comment_mv1_item", true);
        coment = coment.replaceAll("Ответить", "\n\n");
        coment = coment.replaceAll("Нравится", "");
        coment = coment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        coment = coment.replaceAll("\\d{2}:\\d{2}:\\d{2}", "");
        book.setComent(coment);

        String url = getElementAttr(document, "cover-book", "style");
        url = url.replace("background-image: url('", "");
        url = url.replace("');", "");
        book.setUrl(url);

        return book;
    }

    public Autor giveAutor(Document document, String nickname) {

        String name = getString(document, "author-name bold", true);
        String bio = getString(document, "author-bio", true);

        Autor autor = new Autor(nickname);
        autor.setName(name);
        autor.setBio(bio);

        String url = getElementAttr(document, "user-avatar", "style");
        url = url.replace("background-image: url('", "");
        url = url.replace("');", "");
        autor.setUrl(url);

        String info = "";

        info += "Имя " + name + "\n";
        info += "Статус " + bio + "\n";

        List<String> names = getElementStrings(document, "info-stats-name");
        List<String> values = getElementStrings(document, "info-stats-num");

        for(int i = 0; i < names.size(); i++)
            info += names.get(i) + ": " + values.get(i) + "\n";

        info += calculateAuthorBooks(autor);
        autor.setInfo(info);


        return autor;
    }

    public String calculateAuthorBooks(Autor autor){
        Document booksDoc = null;
        try {
            booksDoc = Jsoup.connect("https://surgebook.com/" + autor.getNickname() + "/books/all").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text = "\nСписок книг: \n";
        ArrayList <String> textUrlBooks = new ArrayList<>();

        List<String> books = getElementStrings(booksDoc, "book_view_mv1v2_title");
        List<String> booksUrl = getElementsStrings(booksDoc, "book_view_mv1v2_title", "href");

        for(int i = 0; i < books.size(); i++) {
            text += books.get(i) + "\n";
            textUrlBooks.add(booksUrl.get(i));
        }

        int valuesLikesBooks = 0;
        int valuesCommentsBooks = 0;
        int valuesViewsBooks = 0;

        for(int i = 0; i < textUrlBooks.size(); i++) {
            try {
                booksDoc = Jsoup.connect(textUrlBooks.get(i).toString()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> elements = getElementStrings(booksDoc, "font-size-14 color-white ml-5");
            valuesLikesBooks += Integer.valueOf(elements.get(0));
            valuesCommentsBooks += Integer.valueOf(elements.get(1));
            valuesViewsBooks += Integer.valueOf(elements.get(2));
        }

        text += "\n\nКоличество лайков на книгах: " + valuesLikesBooks + "\n";
        text += "Количество просмотров на книгах: " + valuesViewsBooks + "\n";
        text += "Количество коментариев на книгах: " + valuesCommentsBooks + "\n";

        return text;
    }

}
