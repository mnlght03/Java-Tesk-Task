package ru.softaria.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UrlTablesComparatorTest {
    @Test
    public void givenEqualKeySets_thenDeletedUrlsShouldBeEmpty() {
        Set<String> prevKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        Set<String> currentKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));

        assertTrue(UrlTablesComparator.getDeletedUrls(prevKeySet, currentKeySet).isEmpty());
    }

    @Test
    public void givenEqualKeySets_thenAddedUrlsShouldBeEmpty() {
        Set<String> prevKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        Set<String> currentKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        assertTrue(UrlTablesComparator.getAddedUrls(prevKeySet, currentKeySet).isEmpty());
    }

    @Test
    public void givenEqualTables_thenModifiedUrlsShouldBeEmpty() {
        HashMap<String, String> previousMap = new HashMap<>();
        previousMap.put("url1", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """);

        HashMap<String, String> currentMap = new HashMap<>();
        currentMap.put("url1", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """);

        assertTrue(UrlTablesComparator.getModifiedUrls(currentMap, previousMap).isEmpty());
    }

    @Test
    public void givenPreviousKeySetLongerThanCurrent_thenDeletedUrlsShouldNotBeEmpty() {
        Set<String> prevKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        Set<String> currentKeySet = new HashSet<>(List.of("url1", "example.com", "softaria.ru"));
        assertFalse(UrlTablesComparator.getDeletedUrls(prevKeySet, currentKeySet).isEmpty());
    }

    @Test
    public void givenPreviousKeySetLongerThanCurrent_thenDeletedUrlsShouldBeCorrect() {
        Set<String> prevKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        Set<String> currentKeySet = new HashSet<>(List.of("url1", "example.com", "softaria.ru"));
        assertEquals(
                UrlTablesComparator.getDeletedUrls(prevKeySet, currentKeySet),
                new HashSet<>(List.of("url2", "some.url.org"))
        );
    }

    @Test
    public void givenCurrentKeySetLongerThanCurrent_thenAddedUrlsShouldNotBeEmpty() {
        Set<String> prevKeySet = new HashSet<>(List.of("url1", "url2", "some.url.org"));
        Set<String> currentKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        assertFalse(UrlTablesComparator.getAddedUrls(prevKeySet, currentKeySet).isEmpty());
    }

    @Test
    public void givenCurrentKeySetLongerThanCurrent_thenAddedUrlsShouldBeCorrect() {
        Set<String> prevKeySet = new HashSet<>(List.of("url1", "url2", "some.url.org"));
        Set<String> currentKeySet = new HashSet<>(List.of("url1", "url2", "example.com", "softaria.ru", "some.url.org"));
        assertEquals(
                UrlTablesComparator.getAddedUrls(prevKeySet, currentKeySet),
                new HashSet<>(List.of("example.com", "softaria.ru"))
        );
    }

    @Test
    public void givenSameKeySetWithDifferentValues_thenModifiedUrlsShouldNotBeEmpty() {
        HashMap<String, String> previousMap = new HashMap<>();
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

        HashMap<String, String> currentMap = new HashMap<>();
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

        assertFalse(UrlTablesComparator.getModifiedUrls(previousMap, currentMap).isEmpty());
    }

    @Test
    public void givenSameKeySetWithDifferentValues_thenModifiedUrlsShouldBeCorrect() {
        HashMap<String, String> previousMap = new HashMap<>();
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
        previousMap.put("url2", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is another div</div>
                </body>
                </html>
                """);

        HashMap<String, String> currentMap = new HashMap<>();
        currentMap.put("url1", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """);
        currentMap.put("url2", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """);

        assertEquals(
                UrlTablesComparator.getModifiedUrls(previousMap, currentMap),
                new HashSet<>(List.of("url2"))
        );
    }

    @Test
    public void givenTablesWithDifferentKeySet_thenShouldNotThrow() {
        HashMap<String, String> previousMap = new HashMap<>();
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
        previousMap.put("url2", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is another div</div>
                </body>
                </html>
                """);

        HashMap<String, String> currentMap = new HashMap<>();
        currentMap.put("url1", """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """);

        assertDoesNotThrow(() -> UrlTablesComparator.getModifiedUrls(previousMap, currentMap));
    }
}
