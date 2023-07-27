package ru.softaria.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HtmlComparatorTest {
    @Test
    public void givenSameHtmlStrings_thenReturnTrue() {
        String h1 = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                    <p class="bar" id="2">This is a paragraph</p>
                    <div>
                        <img src="/path/to/image.png" alt="image">
                    </div>
                </body>
                </html>
                """;

        String h2 = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                </head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                    <p class="bar" id="2">This is a paragraph</p>
                    <div>
                        <img src="/path/to/image.png" alt="image">
                    </div>
                </body>
                </html>
                """;
        assertTrue(HtmlComparator.compare(h1, h2));
    }

    @Test
    public void givenElementWithDifferentAttributes_thenReturnFalse() {
        String h1 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div class="foo" id="1">This is a div</div>
                </body>
                </html>
                """;

        String h2 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div class="foo" style="bar" id="1">This is a div</div>
                </body>
                </html>
                """;

        assertFalse(HtmlComparator.compare(h1, h2));
    }

    @Test
    public void givenElementWithSameAttributes_inDifferentOrder_thenReturnTrue() {
        String h1 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div class="foo" style="bar" id="1">This is a div</div>
                </body>
                </html>
                """;

        String h2 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div style="bar" id="1" class="foo">This is a div</div>
                </body>
                </html>
                """;

        assertTrue(HtmlComparator.compare(h1, h2));
    }

    @Test
    public void givenElementWithDifferentText_thenReturnFalse() {
        String h1 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div style="bar" id="1" class="foo">This is a div</div>
                </body>
                </html>
                """;

        String h2 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div style="bar" id="1" class="foo">This is another div</div>
                </body>
                </html>
                """;

        assertFalse(HtmlComparator.compare(h1, h2));
    }

    @Test
    public void givenSameHtmlWithDifferentIndentation_thenReturnTrue() {
        String h1 = """
                <!DOCTYPE html>
                <html lang="en">
                <head></head>
                <body>
                    <div style="bar" id="1" class="foo">This is a div</div>
                </body>
                </html>
                """;

        String h2 = """
                <!DOCTYPE html>
                <html lang="en">
                                    <head></head>
                <body>
                    <div style="bar" id="1" class="foo">
                    This
                     is a
                      div
                     </div>
                </body>
                
                </html>
                
                """;

        assertTrue(HtmlComparator.compare(h1, h2));
    }
}
