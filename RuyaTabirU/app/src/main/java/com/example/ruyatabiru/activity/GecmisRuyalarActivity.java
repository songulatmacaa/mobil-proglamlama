package com.example.ruyatabiru.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.adapter.GecmisRuyalarAdapter;
import com.example.ruyatabiru.database.RuyaGecmisiDBHelper;
import com.example.ruyatabiru.model.RuyaYorum;

import java.util.List;

public class GecmisRuyalarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtBos;
    private GecmisRuyalarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis_ruyalar);

        // XML'deki ID'lerle eşleşen view'lar
        recyclerView = findViewById(R.id.recyclerView);       // Eski recyclerViewGecmis -> recyclerView
        txtBos = findViewById(R.id.txtBos);                   // Eski txtBosMesaj -> txtBos
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Veritabanı işlemleri
        RuyaGecmisiDBHelper dbHelper = new RuyaGecmisiDBHelper(this);
        List<RuyaYorum> ruyaYorumListesi = dbHelper.tumRuyalariGetir();

        // Veri varsa göster, yoksa boş mesajı aç
        if (ruyaYorumListesi == null || ruyaYorumListesi.isEmpty()) {
            txtBos.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            txtBos.setText("Henüz kaydedilmiş rüya yok.");
        } else {
            txtBos.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new GecmisRuyalarAdapter(this, ruyaYorumListesi);
            recyclerView.setAdapter(adapter);
        }
    }
}
