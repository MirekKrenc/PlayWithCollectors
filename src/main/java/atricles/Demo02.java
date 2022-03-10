package main.java.atricles;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo02 {

    public static void main(String[] args) {

        Set<Article> articles = Article.readAllArticles();

        Map<Integer, Long> numberOfArticlesPerYear = articles.stream()
                .filter(article -> article.getYear() > 1900)
//                .limit(10000)
                .collect(Collectors.groupingBy(
                        Article::getYear, Collectors.counting()
                ));

        System.out.println(numberOfArticlesPerYear.entrySet().stream()
                .max(Comparator.comparing(
                        entry -> entry.getValue()
                )).get().getValue());

        Map.Entry<Integer, Long> maxNumberOfArticlesInYear = numberOfArticlesPerYear.entrySet().stream()
                .max(Comparator.comparing(
                        entry -> entry.getValue()
                )).get();

        System.out.println(maxNumberOfArticlesInYear);

        Map.Entry<Long, List<Map.Entry<Integer, Long>>> allMaxesOfArticlesPerYears = numberOfArticlesPerYear.entrySet().stream()
                .collect(
                        Collectors.groupingBy(
                                entry -> entry.getValue()
                        )
                ).entrySet().stream()
                .max(Comparator.comparing(
                        entry -> entry.getKey()
                )).get();

        System.out.println(allMaxesOfArticlesPerYears);

        System.out.println(Stream.of("a", "b", "c", "d")
                .collect(
                        () -> new StringBuilder(),
                        (a, b) -> a.append(b).append(", "),
                        (a1, a2) -> a1.append(a2)
                ));

    }
}
