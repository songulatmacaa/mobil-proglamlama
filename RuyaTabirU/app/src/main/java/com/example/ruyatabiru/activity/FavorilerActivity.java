package com.example.ruyatabiru.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.adapter.AltKategoriAdapter;
import com.example.ruyatabiru.database.FavoriDBHelper;
import com.example.ruyatabiru.model.AltKategori;

import java.util.List;

/**
 * Bu ekran SQLite veritabanındaki favori rüyaları listeler.
 * Hiç favori yoksa kullanıcıya bilgi mesajı gösterilir.
 */
public class FavorilerActivity extends AppCompatActivity {

    private FavoriDBHelper db;
    private RecyclerView recyclerView;
    private TextView bosFavoriMesaji;
    private AltKategoriAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriler);

        // Arayüz bileşenlerini tanımla
        recyclerView = findViewById(R.id.favoriRecyclerView);
        bosFavoriMesaji = findViewById(R.id.bosFavoriMesaji);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Favori veritabanından veri al
        db = new FavoriDBHelper(this);
        List<AltKategori> favoriList = db.tumFavorileriAl();

        // Favori yoksa mesaj göster
        if (favoriList.isEmpty()) {
            bosFavoriMesaji.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            bosFavoriMesaji.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new AltKategoriAdapter(this, favoriList);
            recyclerView.setAdapter(adapter);
        }
    }
}
