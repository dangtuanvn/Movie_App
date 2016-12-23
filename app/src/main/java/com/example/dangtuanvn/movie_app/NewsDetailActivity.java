package com.example.dangtuanvn.movie_app;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sinhhx on 11/9/16.
 */
public class NewsDetailActivity extends AppCompatActivity {
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        extras = getIntent().getExtras();
        String data = extras.getString("data");
        WebView webview = (WebView)findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadData(data, "text/html; charset=utf-8","UTF-8");
    }
}