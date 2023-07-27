package ru.softaria.test;

import java.util.HashMap;
import java.util.Set;

public class UrlTablesAccountant {
    private String name;
    private String email;

    UrlTablesAccountant(String name, String email) {
        this.name = name;
        this.email = email;
    }

    private HashMap<String, String> getPreviousPagesMap() {
        // Logic to fetch previous URLs and HTML pages
        return new HashMap<>();
    }

    private HashMap<String, String> getCurrentPagesMap() {
        // Logic to fetch current URLs and HTML pages
        return new HashMap<>();
    }

    public void compareAndSendResponse() {
        HashMap<String, String> previousPageMap = getPreviousPagesMap();
        HashMap<String, String> currentPageMap = getCurrentPagesMap();

        String response = compareAndGetResponse(previousPageMap, currentPageMap);

        // Logic to send response to specified email
    }

    public String compareAndGetResponse(HashMap<String, String> previousPageMap, HashMap<String, String> currentPageMap) {
        String deletedUrls = getDiffStringFromSet(
                UrlTablesComparator.getDeletedUrls(previousPageMap.keySet(), currentPageMap.keySet())
        );

        String addedUrls = getDiffStringFromSet(
                UrlTablesComparator.getAddedUrls(previousPageMap.keySet(), currentPageMap.keySet())
        );

        String modifiedUrls = getDiffStringFromSet(
                UrlTablesComparator.getModifiedUrls(previousPageMap, currentPageMap)
        );

        String template = """
               Здравствуйте, дорогая %s
                       
               За последние сутки во вверенных Вам сайтах произошли следующие изменения:
               
               Исчезли следующие страницы: {%s}
               Появились следующие новые страницы: {%s}
               Изменились следующие страницы: {%s}
               
               С уважением,
               автоматизированная система
               мониторинга.
            """;

        return String.format(template, this.name, deletedUrls.toString(), addedUrls.toString(), modifiedUrls.toString());
    }

    private String getDiffStringFromSet(Set<String> diff) {
        return diff.isEmpty() ?
                "Никаких изменений нет" :
                diff.stream()
                        .reduce("", (str, url) -> str + (str.isBlank() ? "" : ", ") + url);
    }
}
