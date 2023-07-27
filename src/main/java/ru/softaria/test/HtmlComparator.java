package ru.softaria.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.IntStream;

public class HtmlComparator {
    public static boolean compare(String h1, String h2) {
        return compare(Jsoup.parse(h1), Jsoup.parse(h2));
    }

    public static boolean compare(Document doc1, Document doc2) {
        return compare(doc1.root(), doc2.root());
    }

    public static boolean compare(Element root1, Element root2) {
        if (root1 == root2)
            return true;

        if (root1 == null || root2 == null)
            return false;

        Elements elements1 = root1.getAllElements();
        Elements elements2 = root2.getAllElements();

        if (elements1.size() != elements2.size())
            return false;

        return IntStream
                .range(0, elements1.size())
                .allMatch(
                        i -> compareAttributes(elements1.get(i), elements2.get(i)) &&
                        compareTextStripIndent(elements1.get(i), elements2.get(i))
                );
    }

    public static boolean compareAttributes(Element e1, Element e2) {
        return e1.attributes().equals(e2.attributes());
    }

    public static boolean compareText(Element e1, Element e2) {
        return e1.text().equals(e2.text());
    }

    public static boolean compareTextStripIndent(Element e1, Element e2) {
        String t1 = e1.text().stripIndent();
        String t2 = e2.text().stripIndent();
        return t1.equals(t2);
    }
}
