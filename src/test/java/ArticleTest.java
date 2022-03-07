package test.java;

import main.java.atricles.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    void readAllArticles() {
        Article article = Article.readAllArticles()
                .stream()
                .findFirst()
                .get();

        System.out.println(article);
        assertNotNull(article);

    }
}