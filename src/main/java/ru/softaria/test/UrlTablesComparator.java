package ru.softaria.test;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class UrlTablesComparator {
    public static Set<String> getDeletedUrls(Set<String> prevKeySet, Set<String> currKeySet) {
        return prevKeySet.stream().filter(key -> !currKeySet.contains(key)).collect(Collectors.toSet());
    }

    public static Set<String> getAddedUrls(Set<String> prevKeySet, Set<String> currKeySet) {
        return currKeySet.stream().filter(key -> !prevKeySet.contains(key)).collect(Collectors.toSet());
    }

    public static Set<String> getModifiedUrls(HashMap<String, String> prev, HashMap<String, String> current) {
        return prev.keySet().stream()
                .filter(key -> current.containsKey(key) &&
                            !HtmlComparator.compare(prev.get(key), current.get(key)))
                .collect(Collectors.toSet());
    }
}
