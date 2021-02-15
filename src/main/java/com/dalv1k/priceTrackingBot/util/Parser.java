package com.dalv1k.priceTrackingBot.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Parser {

    public static int parseEuro(String link) {

        try {
            Document document = Jsoup.connect(link)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            String qwe = document.toString().replaceAll("[\\s]", "");
            String[] q = qwe.split("\"offers\":\\{\"@type\":\"Offer\",\"priceCurrency\":\"PLN\",\"price\"");
            q = q[1].split("priceValidUntil");
            String priceStr = q[0].replaceAll("[^0-9]", "");
            return Integer.parseInt(priceStr) / 100;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int parseMediaExpert(String link) {
        int price;
        try {
            Document document = Jsoup.connect(link)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();

            Element element = document.select("div.widget.promo_price_widget").first();
            if (element == null) {
                element = document.select("span.a-price_price").first();
                if (element != null) {
                    String text = element.text();
                    String priceStr = text.replaceAll("[^0-9]", "");
                    price = Integer.parseInt(priceStr);
                    return price;
                } else {
                    element = document.select("div.main-price.is-big").first();
                    String text = element.text();
                    String priceStr = text.replaceAll("[^0-9]", "");
                    price = Integer.parseInt(priceStr);
                    price = price / 100;
                    return price;
                }
            } else {
                String text = element.text();
                String priceStr = text.replaceAll("[^0-9]", "");
                price = Integer.parseInt(priceStr);
                price = price / 100;
                return price;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Parser1: " + e);
            price = 0;
        } catch (IOException e) {
            price = 0;
            System.out.println("Parser2: " + e);
        } catch (NullPointerException e) {
            System.out.println("Parser3: " + e);
            price = 0;
        }
        return price;
    }
}