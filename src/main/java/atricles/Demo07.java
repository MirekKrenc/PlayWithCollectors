package main.java.atricles;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Demo07 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

        System.out.println(Stream.of("a", "b", "c", "d")
                .collect(
                        //Collector<T, A, R>
                        //T - type elements of stream, A - mutable container, R - final container
                        //we often have A = R (often when the finisher is an identity)
                        () -> new StringBuilder(),      //public Supplier<A> supplier();    A - mutable container
                        (a, t) -> a.append(t),          //BiConsumer<A, T> accumulator();   T - processed element
                        (sb1, sb2) -> sb1.append(sb2)   //BinaryOperator<A> combiner();     often the typ returned

                ).toString());

        Map<Integer, List<String>> mapLengthsOfStrings = Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
                .collect(
                        groupingBy(
                                String::length
                        )
                );

        //String -                      T - type of Stream
        //? -                           A - intermediate accumulator type not known
        //Map<Integer, List<String>> -  R - combiner
        Collector<String, ?, Map<Integer, List<String>>> collector = groupingBy(String::length);

//        Collector<String, ?, Map<Integer, List<Value>>> collector1 =
//                groupingBy(
//                        String::length,
//                        Collector<String, ?, Value>
//                );

        //articles with the biggest counting of authors
        System.out.println(articles.stream()
                        .filter(article -> article.getYear() > 1900)
                .collect(
                        Collectors.toMap(
                                a -> a,
                                a -> a.getAuthors().size()
                        )
                ).entrySet().stream()
                .max(
                        Comparator.comparing(entry -> entry.getValue())
                ).get());

        //articles with the biggest counting of authors 2
        Article articleWithMaxNumberOfAuthors = articles.stream()
                .filter(article -> article.getYear() > 1900)
                .max(
                        Comparator.comparing(a -> a.getAuthors().size())
                ).get();

        System.out.println(articleWithMaxNumberOfAuthors.getAuthors().size());

        //article with the most authors for each year
        Stream<Article> articleStream = articles.stream()
                .filter(article -> article.getYear() > 1900)
                //                .limit(10)
                .collect(
                        groupingBy(
                                Article::getYear,
                                Collectors.maxBy(
                                        Comparator.comparing(
                                                a -> a.getAuthors().size()
                                        )
                                )
                        )
                ).entrySet().stream()
                .map(a -> a.getValue().get());

        System.out.println(articleStream
                .filter(a -> a.getYear() == 2005)
                .collect(
                        Collectors.toList()
                ));

        //article with the most authors for each year 2 - since JDK 9 there is filtering in Collector
        //finisher
//        Collector<Article, ?, Article> customCollector = Collectors.filtering(
//                article -> article.getYear() > 1900,
//                Collectors.collectingAndThen(
//                        Collectors.maxBy(
//                                Comparator.comparing(
//                                        a -> a.getAuthors().size()
//                                )
//                                //finisher
//                        ), Optional::get
//                )
//        );


        Collector<Article, ?, Article> customCollector = Collectors.collectingAndThen(
                Collectors.maxBy(
                        Comparator.comparing(
                                a -> a.getAuthors().size()
                        )
                )
                , Optional::get
        );


        System.out.println(articles.stream()
                .collect(
                        groupingBy(
                                a -> a.getYear(),
                                customCollector
                        )
//                        customCollector
                ));

//        Map<Integer, Article> map = articles.stream()
//                .collect(
//                        groupingBy(
//                                Article::getYear,
//                                customCollector
//                        )
//                );
//
//        System.out.println(map);

    }
}
