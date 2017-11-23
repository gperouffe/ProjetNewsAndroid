package fr.centrale.projetnews.POJO;

import java.util.ArrayList;

/**
 * Created by Guillaume on 23/11/2017.
 */

public class ResSources {

    private String status;
    private ArrayList<NewsSource> sources;

    public ResSources(){
        this.status = "";
        this.sources = new ArrayList<NewsSource>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<NewsSource> getSources() {
        return sources;
    }

    public void setSources(ArrayList<NewsSource> sources) {
        this.sources = sources;
    }
}
