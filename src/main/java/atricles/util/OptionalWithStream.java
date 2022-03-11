package main.java.atricles.util;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalWithStream<T>{

    private final Optional<T> optional;

    public OptionalWithStream(Optional<T> optional) {
        this.optional = optional;
    }

    public Stream<T> stream() {
        return !optional.isPresent() ? Stream.empty() : Stream.of(optional.get());
    }
}
