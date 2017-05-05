package com.example.ivonneortega.the_news_project.webview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.detailView.CollectionDemoActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        String url = getIntent().getStringExtra(CollectionDemoActivity.URL);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
