package com.example.bt_video;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnBluetooth, btnWifi, btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBluetooth = findViewById(R.id.btnBluetooth);
        btnWifi = findViewById(R.id.btnWifi);
        btnCamera = findViewById(R.id.btnCamera);

        btnBluetooth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Bluetooth.class);
            startActivity(intent);
        });

        btnWifi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WifiActivity.class); // GÜNCELLENDİ
            startActivity(intent);
        });

        btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class); // GÜNCELLENDİ
            startActivity(intent);
        });
    }
}
