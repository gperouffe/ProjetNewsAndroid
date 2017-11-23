package fr.centrale.projetnews.Activities;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fr.centrale.projetnews.Adapters.ArticleAdapter;
import fr.centrale.projetnews.Fragments.ArticleFragment;
import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

public class NewsActivity extends AppCompatActivity implements ArticleFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.articles, ArticleFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
