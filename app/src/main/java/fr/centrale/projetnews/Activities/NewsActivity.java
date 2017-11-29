package fr.centrale.projetnews.Activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import fr.centrale.projetnews.Adapters.ArticleAdapter;
import fr.centrale.projetnews.Fragments.ArticleFragment;
import fr.centrale.projetnews.Fragments.SourceFragment;
import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

public class NewsActivity extends AppCompatActivity implements ArticleFragment.OnFragmentInteractionListener, SourceFragment.OnFragmentInteractionListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.articles, ArticleFragment.newInstance())
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sources, SourceFragment.newInstance())
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
