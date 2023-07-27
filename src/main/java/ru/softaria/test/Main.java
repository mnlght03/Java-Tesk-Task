package ru.softaria.test;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        var accountant = new UrlTablesAccountant("И.О. Секретаря", "some@email.ru");

        var previousMap = new HashMap<String, String>();
        previousMap.put("url1", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """);

        var currentMap = new HashMap<String, String>();
        currentMap.put("url1", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is another div</div>
                </body>
                </html>
                """);

        String response = accountant.compareAndGetResponse(previousMap, currentMap);

        System.out.println(response);
    }
}
