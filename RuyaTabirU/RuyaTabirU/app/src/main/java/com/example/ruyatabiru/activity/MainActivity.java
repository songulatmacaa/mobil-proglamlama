package com.example.ruyatabiru.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.adapter.KategoriAdapter;
import com.example.ruyatabiru.model.Kategori;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 100;
    private static final String CHANNEL_ID = "ruyatabiru_kanali";

    private RecyclerView kategoriRecyclerView;
    private KategoriAdapter kategoriAdapter;
    private List<Kategori> kategoriList;

    private Button btnFavoriler, btnRuyaYaz, btnGecmisRuyalar;
    private ImageButton btnAra;
    private SearchView kategoriSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        checkNotificationPermission();

        bindViews();
        setupClickListeners();
        setupSearchView();
        setupRecyclerView();
        setupAraButton();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel kanal = new NotificationChannel(
                    CHANNEL_ID,
                    "Rüya Bildirimleri",
                    NotificationManager.IMPORTANCE_DEFAULT);
            kanal.setDescription("Rüya yorumları hakkında bilgilendirme");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(kanal);
            }
        }
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE);
            } else {
                gosterBildirim();
            }
        } else {
            gosterBildirim();
        }
    }

    private void bindViews() {
        btnFavoriler = findViewById(R.id.btnFavoriler);
        btnRuyaYaz = findViewById(R.id.btnRuyaYaz);
        btnGecmisRuyalar = findViewById(R.id.btnGecmisRuyalar);
        btnAra = findViewById(R.id.btnAra);
        kategoriSearchView = findViewById(R.id.kategoriSearchView);
        kategoriRecyclerView = findViewById(R.id.kategoriRecyclerView);
    }

    private void setupClickListeners() {
        btnFavoriler.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FavorilerActivity.class)));

        btnRuyaYaz.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RuyaniYazActivity.class)));

        btnGecmisRuyalar.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GecmisRuyalarActivity.class)));
    }

    private void setupSearchView() {
        kategoriSearchView.setQueryHint("Kategori ara...");
        kategoriSearchView.setIconifiedByDefault(false);

        // Yazı rengini koyu yap
        int id = kategoriSearchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = kategoriSearchView.findViewById(id);
        if (searchText != null) {
            searchText.setTextColor(Color.BLACK);  // Koyu siyah
            searchText.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    private void setupRecyclerView() {
        kategoriRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        kategoriList = new ArrayList<>();
        kategoriList.add(new Kategori("Hayvanlar", R.drawable.hayvanlar));
        kategoriList.add(new Kategori("Renkler", R.drawable.renkler));
        kategoriList.add(new Kategori("Doğa", R.drawable.doga));
        kategoriList.add(new Kategori("İnsanlar", R.drawable.insanlar));
        kategoriList.add(new Kategori("Eşyalar", R.drawable.esyalar));
        kategoriList.add(new Kategori("Duygular", R.drawable.duygular));
        kategoriList.add(new Kategori("Yiyecekler", R.drawable.yiyecekler));
        kategoriList.add(new Kategori("Mekanlar", R.drawable.mekanlar));

        kategoriAdapter = new KategoriAdapter(this, kategoriList);
        kategoriRecyclerView.setAdapter(kategoriAdapter);
    }

    private void setupAraButton() {
        btnAra.setOnClickListener(v -> {
            String aramaMetni = kategoriSearchView.getQuery().toString().trim();
            if (!aramaMetni.isEmpty() && kategoriAdapter != null) {
                kategoriAdapter.getFilter().filter(aramaMetni);
                btnAra.setVisibility(View.GONE);
            }
        });

        kategoriSearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            btnAra.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        });

        kategoriSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                kategoriAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kategoriAdapter.getFilter().filter(newText);
                btnAra.setVisibility(!newText.trim().isEmpty() ? View.VISIBLE : View.GONE);
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gosterBildirim();
        }
    }

    private void gosterBildirim() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("RüyaTabiru")
                .setContentText("Yeni rüya tabiri eklendi!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
    }
}
