package com.example.ruyatabiru.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;

public class DeepSeekWebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt_webview); // Aynı WebView layout'u kullanılıyor

        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JS açık olmalı

        webView.setWebViewClient(new WebViewClient());

        // ✅ DeepSeek'in sohbet sayfası
        webView.loadUrl("https://chat.deepseek.com/");
    }
}
