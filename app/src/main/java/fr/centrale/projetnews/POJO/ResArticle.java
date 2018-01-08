package fr.centrale.projetnews.POJO;

import java.util.ArrayList;

/**
 * Created by Guillaume on 23/11/2017.
 */

public class ResArticle {

    private String status;
    private Integer totalResults;
    private ArrayList<NewsArticle> articles;

    public ResArticle(){
        this.status = "";
        this.articles = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsArticle> articles) {
        this.articles = articles;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
