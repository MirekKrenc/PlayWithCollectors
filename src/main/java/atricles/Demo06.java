package main.java.atricles;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo06 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

        //number of articles per year
        Map<Integer, Long> numberOfArticlePerYear = articles.stream()
                .collect(
                        groupingByAndCounting()
                );

        System.out.println(numberOfArticlePerYear.size());

        //all maxes number of article per year
        Map.Entry<Long, List<Map.Entry<Integer, Long>>> maxArtPerYear = numberOfArticlePerYear.entrySet().stream() //Stream<Map.Entry<Integer, Long>>
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getValue()
                        )
                ).entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getKey())).get();

        System.out.println(maxArtPerYear);

        Map.Entry<Long, List<Integer>> maxArtPerListofYear = numberOfArticlePerYear.entrySet().stream() //Stream<Map.Entry<Integer, Long>>
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getValue,
                                Collectors.mapping(
                                        entry -> entry.getKey(),
                                        Collectors.toList()
                                )
                        )
                ).entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getKey())).get();

        System.out.println(maxArtPerListofYear);

        Map.Entry<Long, List<Integer>> MaxArticlesPerYearAsList2 = numberOfArticlePerYear.entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getValue()
                        )
                ).entrySet().stream()  //Stream<Map.Entry<Long, List<Map.Entry<Integer, Long>>>>
                //i want to get Long and List of Integer
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue().stream().map(e -> e.getKey())
                                        .collect(Collectors.toList())
                        )
                )
                .entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getKey())).get();

        System.out.println(MaxArticlesPerYearAsList2);

    }

    private static <S extends ArticleManipulation> Collector<S, ?, Map<Integer, Long>> groupingByAndCounting() {
        return Collectors.groupingBy(
                S::getYear,
                Collectors.counting()
        );
    }
}
