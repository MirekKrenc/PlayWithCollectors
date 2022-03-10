package main.java.atricles;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Demo05 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

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

        Map.Entry<Long, List<Integer>> maxArtInYear = articlesPerYear
                .entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getValue(), //classifier
                                Collectors.mapping(
                                        entry -> entry.getKey(),
                                        Collectors.toList()
                                )
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

        Map<Long, List<Map.Entry<Integer, Long>>> numberOfArtPerYear = articlesPerYear.entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                e -> e.getValue()
                        )
                );



    }
}
