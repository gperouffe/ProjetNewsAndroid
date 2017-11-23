package fr.centrale.projetnews.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import fr.centrale.projetnews.ApplicationNews;
import fr.centrale.projetnews.POJO.NewsArticle;
import fr.centrale.projetnews.POJO.NewsSource;
import fr.centrale.projetnews.POJO.ResArticle;
import fr.centrale.projetnews.POJO.ResSources;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

public class SplashActivity extends AppCompatActivity {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSources();
    }

    private void getSources(){

        String url = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        String json = (String)o;
                        try {
                            ResSources response = mapper.readValue(json, ResSources.class);

                            if(response.getStatus().equals("ok")){
                                ArrayList<NewsSource> sources = response.getSources();
                                ((ApplicationNews)getApplication()).setSources(sources);

                                //Récupérer la source préférée ou prendre la première si elle n'existe pas.
                                SharedPreferences pref = getPreferences(Activity.MODE_PRIVATE);
                                String sourceId = pref.getString(Consts.PREF_SOURCE, sources.get(0).getId());

                                //Récupération des articles
                                getArticles(sourceId);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Consts.TAG, error.getMessage());
                    }
                }
        );
        queue.add(stringRequest);
    }

    private void getArticles(String sourceId){

        String url = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=" + sourceId;

        final ObjectMapper mapper = new ObjectMapper();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        String json = (String)o;
                        try {
                            ResArticle response = mapper.readValue(json, ResArticle.class);
                            if(response.getStatus().equals("ok")) {
                                ArrayList<NewsArticle> articles = response.getArticles();
                                ((ApplicationNews)getApplication()).setArticles(articles);


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Consts.TAG, error.getMessage());
                    }
                }
        );
        queue.add(stringRequest);
    }
}
