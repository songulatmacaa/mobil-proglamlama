package com.example.ruyatabiru.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.adapter.AltKategoriAdapter;
import com.example.ruyatabiru.database.VeritabaniYardimcisi;
import com.example.ruyatabiru.model.AltKategori;

import java.util.List;

public class AltKategoriActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AltKategoriAdapter adapter;
    private List<AltKategori> altKategoriList;
    private VeritabaniYardimcisi db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_kategori);

        String kategoriAdi = getIntent().getStringExtra("kategoriAdi");
        if (kategoriAdi != null) {
            setTitle(kategoriAdi);
            Log.d("KONTROL", "Kategori Adı: " + kategoriAdi);
        } else {
            Log.d("KONTROL", "Kategori adı GELMEDİ (null)");
        }

        recyclerView = findViewById(R.id.altKategoriRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Başlık ara...");
        searchView.setIconifiedByDefault(false); // açık halde başlasın

        // Yazı görünmeme sorunu varsa rengi belirle:
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = searchView.findViewById(id);
        if (searchText != null) {
            searchText.setTextColor(getResources().getColor(android.R.color.black));
            searchText.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        }

        db = new VeritabaniYardimcisi(this);

        if (kategoriAdi != null) {
            altKategoriList = db.altKategorileriGetir(kategoriAdi);
            Log.d("KONTROL", "Alt kategori sayısı: " + altKategoriList.size());
        }

        adapter = new AltKategoriAdapter(this, altKategoriList);
        recyclerView.setAdapter(adapter);

        // Arama kutusu dinleyicisi
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
