package com.example.ruyatabiru.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;

public class ChatGPTWebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt_webview);

        String ruyaMetni = getIntent().getStringExtra("ruya");

        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // DeepSeek benzeri bir siteye yönlendirme
        String html = "<html><body>" +
                "<h2>Rüyanız:</h2>" +
                "<p>" + ruyaMetni + "</p>" +
                "<p>Yorum alınıyor... (Buraya DeepSeek UI gömülebilir)</p>" +
                "</body></html>";

        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}
