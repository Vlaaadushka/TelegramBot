package ru.vlaadushka.spsuace.telegram_bot;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        ArrayList<Element> elements = booksDoc.getElementsByClass(name);
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            strings.add(elements.get(i).attr(nameAttr));
        }

        return strings;
    }

    public String getElementAttr(Document document, String name, String nameAttr) {
        Elements elements = document.getElementsByClass(name);
        return elements.attr(nameAttr);
    }
}
