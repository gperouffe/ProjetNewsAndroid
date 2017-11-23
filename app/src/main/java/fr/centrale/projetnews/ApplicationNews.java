package fr.centrale.projetnews;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import fr.centrale.projetnews.POJO.NewsArticle;
import fr.centrale.projetnews.POJO.NewsSource;
import fr.centrale.projetnews.POJO.ResArticle;
import fr.centrale.projetnews.POJO.ResSources;

public class ApplicationNews extends Application {

    protected ArrayList<NewsArticle> articles;
    protected ArrayList<NewsSource> sources;

    public ArrayList<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsArticle> articles) {
        this.articles = articles;
    }

    public ArrayList<NewsSource> getSources() {
        return sources;
    }

    public void setSources(ArrayList<NewsSource> sources) {
        this.sources = sources;
    }
}
