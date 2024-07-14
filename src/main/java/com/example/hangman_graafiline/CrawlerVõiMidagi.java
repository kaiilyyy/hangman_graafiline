package com.example.hangman_graafiline;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlerVõiMidagi {
    public static void main(String[] args) {
        String url1 = "https://sonaveeb.ee/search/unif/dlall/dsall/";
        String url2 = "kana";
        String url3 = "/1";
        //String url = url1 + url2 + url3;
        String url = "https://sonaveeb.ee/search/lite/dlall/kana/1";
        System.out.println(url);

        try {
            Document lehekülg = Jsoup.connect(url).get();

            Elements elements = lehekülg.select(".definition-value.mb-2.mr-1.text-weight-medium.text-dark");

            for (Element element : elements) {
                String value = element.text();
                System.out.println(value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}