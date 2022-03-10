package main.java.atricles;

import java.util.List;

public interface ArticleManipulation {

    public int getYear();

    public String getTitle();

    public String getArticleType();

    public List<Author> getAuthors();

}
