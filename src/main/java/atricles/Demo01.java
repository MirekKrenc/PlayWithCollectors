package main.java.atricles;

import java.net.CookieManager;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class Demo01 {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAllArticles();

        System.out.println(articles.stream()
                .count());

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .map(Article::getYear)
                //.min(Comparator.naturalOrder())
                .collect(Collectors.minBy(Comparator.naturalOrder()))
                .orElseGet(() -> 0));

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .map(Article::getYear)
                //.max(Comparator.naturalOrder())
                .collect(Collectors.maxBy(Comparator.naturalOrder()))
                .orElseGet(() -> 2022));

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .mapToInt(Article::getYear)
                .summaryStatistics());

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .collect(Collectors.summarizingInt(Article::getYear)));

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .limit(3)
                .map(Article::getTitle)
                .collect(Collectors.joining("; ")));
    }
}
