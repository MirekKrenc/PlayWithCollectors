package main.java.atricles.util;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;


/*
definition of intermediate operation
so the calls have to take downstream collector
* */
public class FilteringCollector<T, A, R> implements Collector<T, A, R> {

    private final Predicate<T> predicate;
    private final Collector<T, A, R> downstream;

    public FilteringCollector(Predicate<T> predicate, Collector<T, A, R> downstream) {
        this.predicate = predicate;
        this.downstream = downstream;
    }

    @Override
    public Supplier<A> supplier() {
        return downstream.supplier();
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return (container, element) -> {
            if (predicate.test(element)) {
                downstream.accumulator().accept(container, element);
            }
        };
    }

    @Override
    public BinaryOperator<A> combiner() {
        return downstream.combiner();
    }

    @Override
    public Function<A, R> finisher() {
        return downstream.finisher();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return downstream.characteristics();
    }
}
