package main.java.atricles;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Demo03 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

        Function<Map<Integer, Long>, Map.Entry<Integer, Long>> finisherOfCollector = maxEntryByValue();

        Collector<Article, ?, Map<Integer, Long>> groupingBy = getGroupingByAndCounting(Article::getYear);

        Collector<Article, ?, Map.Entry<Integer, Long>> collectingAndThen = Collectors.collectingAndThen(
                groupingBy, maxEntryByValue()
        );

        System.out.println(articles.stream()
                .filter(a -> a.getYear() > 1900)
                .collect(collectingAndThen));


        Map.Entry<Integer, Long> maxNumberOfArtPerYear =
                articles.stream()
                        .collect(
                                Collectors.collectingAndThen(
                                        getGroupingByAndCounting(Article::getYear),
                                        maxEntryByValue()
                                )
                        );

        System.out.println("Articles per yer = " + maxNumberOfArtPerYear);


    }

    private static <T, V> Collector<T, ?, Map<V, Long>> getGroupingByAndCounting(Function<T, V> classifier) {
        return Collectors.groupingBy(
                classifier,
        Collectors.counting()
);
    }

    private static Function<Map<Integer, Long>, Map.Entry<Integer, Long>> maxEntryByValue1() {
        return map -> map.entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue()))
                .get();
    }

    private static <K, V extends Comparable<? super V>> Function<Map<K, V>, Map.Entry<K, V>> maxEntryByValue() {
        return maxBy(Map.Entry.<K,V>comparingByValue());
    }

    private static <K, V> Function<Map<K, V>, Map.Entry<K, V>> maxBy(Comparator<Map.Entry<K, V>> comparing) {
        return map -> map.entrySet().stream()
                    .max(comparing)
                    .get();
    }

    public static Map.Entry<Integer, Long> maxEntryBy1(Map<Integer, Long> map) {
        return map.entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue()))
                .get();
    }
}
