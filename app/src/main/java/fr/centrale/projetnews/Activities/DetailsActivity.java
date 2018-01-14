package fr.centrale.projetnews.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.POJO.NewsArticle;
import fr.centrale.projetnews.R;

public class DetailsActivity extends AppCompatActivity {

    private NewsArticle article;
    private SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private DateFormat toLocaleDate = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
    private DateFormat toLocaleTime = new SimpleDateFormat("HH'h'mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_details);

        NewsApplication appli = (NewsApplication)getApplication();
        Intent intent = getIntent();
        article = appli.getArticles().get(intent.getIntExtra("article", 0));

        ((TextView) findViewById(R.id.title)).setText(article.getTitle());
        ((TextView) findViewById(R.id.description)).setText(article.getDescription());

        Resources res = getApplicationContext().getResources();
        String author_date;
        Date published = new Date();
        try {
            published = dateParse.parse(article.getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(article.getAuthor() != null){
            author_date = res.getString(R.string.author_date, article.getAuthor(), toLocaleDate.format(published), toLocaleTime.format(published));
        }
        else{
            author_date = res.getString(R.string.publish_date, toLocaleDate.format(published), toLocaleTime.format(published));
        }
        ((TextView) findViewById(R.id.author_date)).setText(author_date);

        ((TextView) findViewById(R.id.source)).setText(res.getString(R.string.read_on, article.getSource().getName()));

        NetworkImageView imageView = findViewById(R.id.image);
        if(article.getUrlToImage() != null) {
            imageView.setImageUrl(article.getUrlToImage(), appli.getImageLoader());
        }
        else{
            imageView.setVisibility(View.GONE);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, WebActivity.class);
                intent.putExtra("url", article.getUrl());
                startActivity(intent) ;
                overridePendingTransition(R.anim.slide_in, R.anim.still);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
