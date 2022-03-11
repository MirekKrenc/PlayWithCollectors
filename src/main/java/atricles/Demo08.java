package main.java.atricles;

import main.java.atricles.util.FilteringCollector;
import main.java.atricles.util.OptionalWithStream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

public class Demo08 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .max(
                        Comparator.comparing(a -> a.getAuthors().size())
                ).get().getAuthors().size());

        Collector<Article, ?, Article> downstreamFilteringCollector = new FilteringCollector<>(
                a -> a.getYear() > 1900,
                collectingAndThen(

                        Collectors.maxBy(Comparator.comparing(a -> a.getAuthors().size())),
                        o -> o.orElse(new Article(1, "", ""))   //BAD IDEA
                )
        );


        Collector<Article, ?, Stream<Article>> downstreamFilteringCollector1 =
                new FilteringCollector<>(
                        a -> a.getYear() > 1900,
                        collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(a -> a.getAuthors().size())),
                                o -> new OptionalWithStream<>(o).stream()
                        )
                );

        Map<Integer, Stream<Article>> collect = articles.stream()
                .collect(
                        groupingBy(
                                Article::getYear,
                                downstreamFilteringCollector1
                        )
                );

        /*
        processing stream with flatMap vanishes empty elements
        while when using Optional::get we can have exception when an element is empty
        * */

        System.out.println(collect.size());

        Map<Integer, Article> articleMap = articles.stream()
                .collect(
                        groupingBy(
                                Article::getYear,
                                downstreamFilteringCollector1
                        )
                ).entrySet().stream()
                .flatMap(
                        entry -> entry.getValue()
                                .map(value -> new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), value))
                )
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey(),
                                entry -> entry.getValue()
                        )
                );

        System.out.println(articleMap.size());
    }
}
