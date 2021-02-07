package com.dalv1k.priceTrackingBot.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Parser {

    public static int parseRozetka(String link){
return 0;
    }

    public static int parseMediaExpert(String link) {
        int price;
        try {
            Document document = new Document("q");
                document = Jsoup.connect(link)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();


            Element element = document.select("div.widget.promo_price_widget").first();
            // System.out.println(element);
            if (element == null) {
                element = document.select("span.a-price_price").first();
                String text = element.text();
                String priceStr = text.replaceAll("[^0-9]", "");
                price = Integer.parseInt(priceStr);
              //  System.out.println("Current price is " + price);
                return price;
            } else {
                System.out.println(element);
                String text = element.text();
                String priceStr = text.replaceAll("[^0-9]", "");
                price = Integer.parseInt(priceStr);
                price = price / 100;
               // System.out.println("Current price is " + price);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Parser: "+e);
            price = 0;
        } catch (IOException e){
            price =0;
            System.out.println("Parser2: "+e);
        }

        return price;
    }
}