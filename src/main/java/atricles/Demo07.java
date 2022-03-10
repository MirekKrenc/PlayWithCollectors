package main.java.atricles;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo07 {

    public static void main(String[] args) {
        Set<Article> articles = Article.readAllArticles();

        System.out.println(Stream.of("a", "b", "c", "d")
                .collect(
                        () -> new StringBuilder(),      //public Supplier<A> supplier();
                        (a, t) -> a.append(t),          //BiConsumer<A, T> accumulator();
                        (sb1, sb2) -> sb1.append(sb2)   //BinaryOperator<A> combiner();

                ).toString());

    }
}
