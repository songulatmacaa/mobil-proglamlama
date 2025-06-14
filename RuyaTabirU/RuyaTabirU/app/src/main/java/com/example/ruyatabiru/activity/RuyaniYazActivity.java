package com.example.ruyatabiru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruyatabiru.R;
import com.example.ruyatabiru.database.RuyaGecmisiDBHelper;

public class RuyaniYazActivity extends AppCompatActivity {

    private EditText ruyaEditText;
    private Button btnYorumla;

    // Bellekte 5 sabit yorum
    private final String[] yorumlar = {
            "Bu rüya, hayatınızda yeni bir başlangıca hazırlanmanız gerektiğine işaret ediyor. Uzun süredir düşündüğünüz bir değişim için artık harekete geçmenin zamanı gelmiş olabilir.",
            "Yakın zamanda alacağınız güzel bir haber, sizi hem duygusal hem de ruhsal anlamda rahatlatacak. Sabırla beklediğiniz gelişmeler olumlu sonuçlanabilir.",
            "Rüyanız, çevrenizdeki bazı kişilere karşı dikkatli olmanız gerektiğini gösteriyor. Güvendiğiniz insanları yeniden gözden geçirmek isteyebilirsiniz.",
            "İç dünyanızda bastırdığınız bazı duygular, bu rüya yoluyla dışa vurulmuş olabilir. Kendinize karşı daha dürüst olmanız gereken bir dönemdesiniz.",
            "Bu rüya, sabır ve özveriyle çalıştığınız konularda yakında meyve toplamaya başlayacağınızı müjdeliyor. Emekleriniz karşılıksız kalmayacak.",
            "Rüyada yaşadığınız belirsizlikler, gerçek hayatta da bir karar vermekte zorlandığınızın göstergesi olabilir. Karar sürecinde sezgilerinize güvenin.",
            "Hayatınızda bazı yüklerden kurtulma isteğiniz bu rüyanın temel mesajıdır. Geçmişi geride bırakıp kendinize yeni bir sayfa açmalısınız.",
            "Bu rüya, sosyal çevrenizden destek görme ihtiyacınızı yansıtıyor olabilir. Duygusal anlamda yalnız hissettiğiniz bir dönemde olabilirsiniz.",
            "Maddi ya da manevi konularda yaşayacağınız küçük bir gelişme, büyük fırsatlara kapı aralayabilir. Bu süreci iyi değerlendirmeniz önem taşıyor.",
            "Rüyanız, içinizde taşıdığınız ama dillendiremediğiniz hayalleri ve arzuları temsil ediyor. Artık kendinizi ifade etmekten korkmamalısınız."
    };

    private static int yorumIndex = 0; // yorum sırasını tutar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruyani_yaz);

        ruyaEditText = findViewById(R.id.ruyaEditText);
        btnYorumla = findViewById(R.id.btnYorumla);

        btnYorumla.setOnClickListener(v -> {
            String kullaniciRuyasi = ruyaEditText.getText().toString().trim();
            if (kullaniciRuyasi.isEmpty()) {
                Toast.makeText(this, "Lütfen bir rüya yazınız.", Toast.LENGTH_SHORT).show();
            } else {
                // Sabit yorum seçimi
                String secilenYorum = yorumlar[yorumIndex];
                yorumIndex = (yorumIndex + 1) % yorumlar.length;

                // Veritabanına kaydet (opsiyonel)
                RuyaGecmisiDBHelper dbHelper = new RuyaGecmisiDBHelper(this);
                dbHelper.yorumEkle(kullaniciRuyasi, secilenYorum);

                // YorumActivity'e geçiş
                Intent intent = new Intent(this, YorumActivity.class);
                intent.putExtra("ruyaMetni", kullaniciRuyasi);
                intent.putExtra("yorumMetni", secilenYorum);
                startActivity(intent);
            }
        });
    }
}
