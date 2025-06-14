package com.example.ruyatabiru.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;

/**
 * Bu activity, AltKategori'den gelen başlık ve açıklamayı detaylı şekilde gösterir.
 */
public class TabirDetayActivity extends AppCompatActivity {

    private TextView baslikTextView, aciklamaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabir_detay);

        // XML bileşenleri ile bağlantı kur
        baslikTextView = findViewById(R.id.detayBaslik);
        aciklamaTextView = findViewById(R.id.detayAciklama);

        // Intent ile gelen verileri al
        String baslik = getIntent().getStringExtra("baslik");
        String aciklama = getIntent().getStringExtra("aciklama");

        // Başlık varsa hem sayfa üstüne hem TextView'e yaz
        if (baslik != null && !baslik.isEmpty()) {
            setTitle(baslik); // ActionBar başlığı
            baslikTextView.setText(baslik);
        } else {
            baslikTextView.setText("Başlık bulunamadı");
        }

        // Açıklama varsa TextView'e yaz
        if (aciklama != null && !aciklama.isEmpty()) {
            aciklamaTextView.setText(aciklama);
        } else {
            aciklamaTextView.setText("Açıklama mevcut değil.");
        }
    }
}
