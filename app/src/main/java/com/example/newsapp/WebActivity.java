package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {
    private static String TAG="WebActivity";
    private WebView myWebView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        myWebView = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();

        if(intent!=null){
            url = intent.getStringExtra("url_key");
            myWebView.loadUrl(url);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
