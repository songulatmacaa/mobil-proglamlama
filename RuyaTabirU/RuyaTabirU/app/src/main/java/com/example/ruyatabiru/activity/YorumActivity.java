package com.example.ruyatabiru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.database.FavoriDBHelper;

public class YorumActivity extends AppCompatActivity {

    private TextView txtRuya, txtYorum;
    private Button btnFavoriEkle, btnPaylas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorum);

        // Görsel öğeleri bağla
        txtRuya = findViewById(R.id.txtRuya);
        txtYorum = findViewById(R.id.txtYorum);
        btnFavoriEkle = findViewById(R.id.btnFavoriEkle);
        btnPaylas = findViewById(R.id.btnPaylas);

        // Intent ile gelen verileri al
        String ruyaMetni = getIntent().getStringExtra("ruyaMetni");
        String yorumMetni = getIntent().getStringExtra("yorumMetni");

        // Ekranda göster
        txtRuya.setText("Rüyanız:\n" + ruyaMetni);
        txtYorum.setText("Yorum:\n" + yorumMetni);

        // Favorilere ekle
        btnFavoriEkle.setOnClickListener(v -> {
            FavoriDBHelper dbHelper = new FavoriDBHelper(this);
            dbHelper.favoriEkle(ruyaMetni, yorumMetni);
            Toast.makeText(this, "Favorilere eklendi", Toast.LENGTH_SHORT).show();
        });

        // Paylaş
        btnPaylas.setOnClickListener(v -> {
            String paylasMetni = "Rüya: " + ruyaMetni + "\n\nYorum: " + yorumMetni;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, paylasMetni);
            startActivity(Intent.createChooser(intent, "Uygulama ile paylaş"));
        });
    }
}
