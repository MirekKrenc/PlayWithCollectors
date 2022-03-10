package main.java.atricles;

import java.security.KeyStore;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Demo04 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

        System.out.println(articles.stream()
                .flatMap(a -> a.getAuthors().stream())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get());

        Map<Integer, Long> articlesPerYear = articles.stream()
                .map(Article::getYear)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        Map.Entry<Integer, Long> maxNumberOfArticlesPerYear = articlesPerYear.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get();

        System.out.println(maxNumberOfArticlesPerYear);

        Map.Entry<Long, List<Map.Entry<Integer, Long>>> maxArtInYear = articlesPerYear
                .entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getValue()
                        )
                )
                .entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getKey()
                        )
                )
                .entrySet().stream()
                .max(Comparator.comparing(
                        e -> e.getKey()
                )).get().getValue().stream().findFirst().get();

        System.out.println(maxArtInYear);

    }
}
