package fr.centrale.projetnews.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);

        if (getIntent().getExtras() != null && getIntent().hasExtra(Consts.EXIT_APP)) {
            finishAndRemoveTask();
        }
        else{
            getSources();
        }
    }

    private void getSources(){

        String url = "https://newsapi.org/v2/sources" +
                "?apiKey=" + Consts.API_KEY +
                "&language=" + Consts.API_LANG;

        progressBar.setVisibility(View.VISIBLE);

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
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        networkFailureDialog();
                        Log.e(Consts.TAG, "Error: " + error.getMessage());
                    }
                }
        );
        queue.add(stringRequest);
    }


    public void networkFailureDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.network_err_title)
                .setMessage(R.string.network_err_msg)
                .setPositiveButton(R.string.network_err_retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSources();
                    }
                })
                .setNegativeButton(R.string.network_err_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAndRemoveTask();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
