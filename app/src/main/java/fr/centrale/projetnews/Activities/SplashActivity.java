package fr.centrale.projetnews.Activities;

import android.app.Activity;
import android.content.Intent;
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

import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.POJO.NewsArticle;
import fr.centrale.projetnews.POJO.NewsSource;
import fr.centrale.projetnews.POJO.ResArticle;
import fr.centrale.projetnews.POJO.ResSources;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

public class SplashActivity extends Activity {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSources();
    }

    private void getSources(){

        String url = "https://newsapi.org/v2/sources" +
                "?apiKey=" + Consts.API_KEY +
                "&language=" + Consts.API_LANG;


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
                                ((NewsApplication)getApplication()).setSources(sources);

                                Intent NewsIntent = new Intent(SplashActivity.this, NewsActivity.class);
                                startActivity(NewsIntent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Consts.TAG, "Error: " + error.getMessage());
                    }
                }
        );
        queue.add(stringRequest);
    }
}
