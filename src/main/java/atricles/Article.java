package main.java.atricles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Article implements ArticleManipulation{
    private final int year;
    private final String title;
    private final String articleType;
    private List<Author> authors;

    public static Set<Article> readAllArticles() {
        Path articlesPath = Paths.get("files/papers.lst");

        try (Stream<String> lines = Files.lines(articlesPath)){
            return lines
                    .map(line -> Article.of(line))
                            .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Set.of();
    }

    private static Article of(String line) {
        int articleYear = extractYear(line);
        String articleType = line.substring(12, 23).trim();
        String authorsAndTitle = line.substring(24).trim();

        int firstComma = authorsAndTitle.indexOf(",");
        if (firstComma == -1) {
            String title = authorsAndTitle;
            Article article = new Article(articleYear,
                    title,
                    articleType);
            article.authors = new ArrayList<>();
            return article;
        } else {
            String[] authors = authorsAndTitle.substring(0, firstComma).split("&");
            String title = authorsAndTitle.substring(firstComma + 1).trim();
            Article article = new Article(articleYear,
                    title,
                    articleType);
            article.authors =
                    Stream.of(Arrays.asList(authors))
                            .flatMap(n -> n.stream())
                            .map(Author::of)
                            .collect(Collectors.toList());
            return article;
        }
    }

    private static int extractYear(String line) {
        String yearAsString = line.substring(0, 4);
        try {
            return Integer.parseInt(yearAsString);
        } catch (NumberFormatException nfe) {
            return yearAsString.endsWith("??") ? 1950 : 1980;
        }

    }

    Article(int year, String title, String articleType) {
        this.year = year;
        this.title = title;
        this.articleType = articleType;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getArticleType() {
        return articleType;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Article{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", articleType='" + articleType + '\'' +
                ", authors=" + authors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return year == article.year && title.equals(article.title) && articleType.equals(article.articleType) && Objects.equals(authors, article.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, title, articleType, authors);
    }
}
