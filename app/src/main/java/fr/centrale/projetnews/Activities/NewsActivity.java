package fr.centrale.projetnews.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import fr.centrale.projetnews.Adapters.ArticleAdapter;
import fr.centrale.projetnews.Fragments.ArticleFragment;
import fr.centrale.projetnews.Fragments.SourceFragment;
import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.POJO.NewsArticle;
import fr.centrale.projetnews.POJO.NewsSource;
import fr.centrale.projetnews.POJO.ResArticle;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

public class NewsActivity extends AppCompatActivity implements ArticleFragment.OnFragmentInteractionListener, SourceFragment.OnFragmentInteractionListener, ArticleAdapter.OnLoadMoreListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<NewsArticle> articles;
    private ArrayList<NewsSource> sources;

    private ArticleFragment articleFragment;
    private SourceFragment sourceFragment;
    private String currentSourceId;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        NewsApplication appli = (NewsApplication) getApplication();
        articles = appli.getArticles();
        sources = appli.getSources();

        //Récupérer la source préférée ou prendre la première si elle n'existe pas.
        SharedPreferences pref = getPreferences(Activity.MODE_PRIVATE);
        currentSourceId = pref.getString(Consts.PREF_SOURCE, sources.get(0).getId());
        for(NewsSource source: sources){
            source.setSelected(source.getId().equals(currentSourceId));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        articleFragment = ArticleFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.articles, articleFragment)
                .commit();

        sourceFragment = SourceFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sources, sourceFragment)
                .commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onArticleFragmentInteraction(int articleId) {
        Intent intent = new Intent(NewsActivity.this, DetailsActivity.class);
        intent.putExtra("article", articleId);
        startActivity(intent)  ;
    }

    @Override
    public void onSourceFragmentInteraction(String sourceId) {
        if(!currentSourceId.equals(sourceId)){
            drawerLayout.closeDrawer(Gravity.LEFT);
            page = 1;

            for(NewsSource source: sources){
                source.setSelected(source.getId().equals(sourceId));
            }
            sourceFragment.notifyDataSetChanged();

            currentSourceId = sourceId;
            SharedPreferences.Editor ed = getPreferences(Activity.MODE_PRIVATE).edit();
            ed.putString(Consts.PREF_SOURCE, sourceId);
            ed.commit();
            getArticles();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadMore() {
        page++;
        this.getArticles();
    }
    public void getArticles(){

        final ArticleAdapter adapter = articleFragment.getArticleAdapter();

        String url = "https://newsapi.org/v2/everything" +
                "?apiKey=" + Consts.API_KEY +
                "&language=" + Consts.API_LANG +
                "&sources=" + currentSourceId ;
        if(page != 1) {
            url += "&page=" + page;
        }
        else
        {
            int size = articles.size();
            articles.clear();
            if(adapter != null){
                adapter.notifyItemRangeRemoved(0, size);
            }
        }
        articles.add(null);
        if(adapter != null){
            adapter.notifyItemInserted(articles.size()-1);
        }

        Log.d(Consts.TAG, url);
        final ObjectMapper mapper = new ObjectMapper();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        String json = (String)o;
                        Log.d(Consts.TAG, json);
                        articles.remove(articles.size()-1);
                        if(adapter != null){
                            adapter.notifyItemRemoved(articles.size());
                        }
                        ResArticle response = new ResArticle();
                        try {
                            response = mapper.readValue(json, ResArticle.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(response.getStatus().equals("ok")) {
                            for (NewsArticle article : response.getArticles()) {
                                articles.add(article);
                                if (adapter != null) {
                                    adapter.notifyItemInserted(articles.size() - 1);
                                }
                            }
                        }
                        if(adapter != null){
                            adapter.loadEnd();
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
