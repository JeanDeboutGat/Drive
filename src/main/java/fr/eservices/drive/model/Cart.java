package fr.eservices.drive.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class Cart {

    List<Article> articles = new ArrayList<>();

    HashMap<Article, Integer> articlesMap = new HashMap<>();

    public Cart() {}


    public List<Article> getArticles() {
        return articles;
    }

    public HashMap<Article, Integer> getArticlesMap() {
        return articlesMap;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void setArticlesMap(HashMap<Article, Integer> articlesmap) {
        this.articlesMap = articlesmap;
    }

    public int getValue(Article article) {
        for (Entry<Article, Integer> entry : this.articlesMap.entrySet()) {
            if (entry.getKey().equals(article)) {
                return entry.getValue();
            }
        }
        return 0;
    }
}
