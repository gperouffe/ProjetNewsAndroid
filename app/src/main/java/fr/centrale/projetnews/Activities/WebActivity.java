package fr.centrale.projetnews.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import fr.centrale.projetnews.R;

public class WebActivity extends AppCompatActivity {

    private WebView webView;

    private class CustomWebViewClient extends WebViewClient{
        private ProgressBar progress;

        public CustomWebViewClient(ProgressBar progress){
            this.progress = progress;
        }

        //Keep redirections inside app
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return false;
        }
        //Show progress bar
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }
        //Hide progress bar
        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.INVISIBLE);
            super.onPageFinished(view, url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_web);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        webView = findViewById(R.id.webview);
        ProgressBar pb = findViewById(R.id.progressBar);
        webView.setWebViewClient(new CustomWebViewClient(pb));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.still, R.anim.slide_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.still, R.anim.slide_out);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
}
