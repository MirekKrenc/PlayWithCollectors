package main.java.atricles;

import java.util.Objects;

public class Author {
    private final String lastName;

    private Author(String lastName) {
        this.lastName = lastName;
    }

    public static Author of(String lastName) {
        return new Author(lastName);
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return lastName.equals(author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName);
    }
}
