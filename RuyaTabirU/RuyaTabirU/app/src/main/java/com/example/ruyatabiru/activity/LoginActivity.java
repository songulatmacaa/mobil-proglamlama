package com.example.ruyatabiru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.database.UserDBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSifre;
    private Button btnGiris;
    private TextView tvKayitOl;
    private UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // XML'deki id'lerle uyumlu olarak değişkenleri bağla
        edtEmail = findViewById(R.id.editTextEmail);
        edtSifre = findViewById(R.id.editTextPassword);
        btnGiris = findViewById(R.id.buttonLogin);
        tvKayitOl = findViewById(R.id.textViewRegister);

        dbHelper = new UserDBHelper(this);

        btnGiris.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String sifre = edtSifre.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(sifre)) {
                Toast.makeText(this, "Lütfen email ve şifre girin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.kullaniciGirisKontrol(email, sifre)) {
                Toast.makeText(this, "Giriş başarılı", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email veya şifre yanlış", Toast.LENGTH_SHORT).show();
            }
        });

        tvKayitOl.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}
