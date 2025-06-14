package com.example.ruyatabiru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.database.UserDBHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtAdSoyad, edtEmail, edtSifre;
    private Button btnKaydol;
    private UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtAdSoyad = findViewById(R.id.edtAdSoyad);
        edtEmail = findViewById(R.id.edtEmail);
        edtSifre = findViewById(R.id.edtSifre);
        btnKaydol = findViewById(R.id.btnKaydol);

        dbHelper = new UserDBHelper(this);

        btnKaydol.setOnClickListener(v -> {
            String adSoyad = edtAdSoyad.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String sifre = edtSifre.getText().toString().trim();

            if (TextUtils.isEmpty(adSoyad) || TextUtils.isEmpty(email) || TextUtils.isEmpty(sifre)) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.kullaniciVarMi(email)) {
                Toast.makeText(this, "Bu email zaten kayıtlı", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean kayitBasarili = dbHelper.kullaniciEkle(adSoyad, email, sifre);
            if (kayitBasarili) {
                Toast.makeText(this, "Kayıt başarılı! Giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Kayıt sırasında hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
