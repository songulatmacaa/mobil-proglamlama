package com.example.ruyatabiru.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.helper.VeriEkle;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SURESI = 2000; // 2 saniye

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // İlk verileri veritabanına ekle (sadece ilk açılışta)
        VeriEkle.ilkVerileriEkle(this);

        // 2 saniye bekledikten sonra LoginActivity’ye yönlendir
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SURESI);
    }
}
