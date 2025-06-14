package com.example.ruyatabiru.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ruyatabiru.database.VeritabaniYardimcisi;

public class VeriEkle {

    public static void ilkVerileriEkle(Context context) {
        SharedPreferences sp = context.getSharedPreferences("ilk_veri", Context.MODE_PRIVATE);
        boolean eklendiMi = sp.getBoolean("eklendi", false);

        if (!eklendiMi) {
            VeritabaniYardimcisi db = new VeritabaniYardimcisi(context);
            db.getWritableDatabase(); // 🔧 Veritabanı ve tabloları başlat

            // ✅ Hayvanlar
            db.altKategoriEkle("Rüyada Aslan Görmek", "Güç ve cesareti temsil eder.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Kedi Görmek", "Bağımsızlık ve sezgiyle ilgilidir.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Yılan Görmek", "Tehlike ve içsel dönüşümü simgeler.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Kuş Görmek", "Özgürlük ve haber anlamına gelir.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Ayı Görmek", "Gizli güç ve öfkeyi temsil eder.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Fare Görmek", "Endişe ve küçük düşmanları simgeler.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Balık Görmek", "Bereket ve derin duygulara işaret eder.", "Hayvanlar");
            db.altKategoriEkle("Rüyada Köpek Görmek", "Sadakat ve koruma simgesidir.", "Hayvanlar");

            // ✅ Renkler
            db.altKategoriEkle("Rüyada Kırmızı Görmek", "Tutku, öfke ve canlılığı temsil eder.", "Renkler");
            db.altKategoriEkle("Rüyada Mavi Görmek", "Huzur ve iletişimi simgeler.", "Renkler");
            db.altKategoriEkle("Rüyada Yeşil Görmek", "Doğa ve umutla ilişkilidir.", "Renkler");
            db.altKategoriEkle("Rüyada Beyaz Görmek", "Saflık ve yeni başlangıçtır.", "Renkler");
            db.altKategoriEkle("Rüyada Siyah Görmek", "Gizem, bilinçaltı ve korkuyu gösterir.", "Renkler");
            db.altKategoriEkle("Rüyada Mor Görmek", "Ruhsal güç ve sezgiyi temsil eder.", "Renkler");
            db.altKategoriEkle("Rüyada Sarı Görmek", "Zeka, enerji ve dikkat çeker.", "Renkler");
            db.altKategoriEkle("Rüyada Turuncu Görmek", "Yaratıcılık ve hareketlilikle ilgilidir.", "Renkler");

            // ✅ Doğa
            db.altKategoriEkle("Rüyada Deniz Görmek", "Bilinçaltı ve duygusal derinliği temsil eder.", "Doğa");
            db.altKategoriEkle("Rüyada Dağ Görmek", "Zorluklar, sabır ve hedef anlamına gelir.", "Doğa");
            db.altKategoriEkle("Rüyada Yağmur Görmek", "Arınma, ferahlama ve yenilenmeyi simgeler.", "Doğa");
            db.altKategoriEkle("Rüyada Güneş Görmek", "Başarı, aydınlanma ve umut verir.", "Doğa");
            db.altKategoriEkle("Rüyada Ay Görmek", "Sezgi, içsel güç ve kadınsı enerjiyi temsil eder.", "Doğa");
            db.altKategoriEkle("Rüyada Yıldız Görmek", "Hedefler, dilekler ve rehberlik anlamına gelir.", "Doğa");
            db.altKategoriEkle("Rüyada Rüzgar Görmek", "Değişim ve özgürlüğü ifade eder.", "Doğa");
            db.altKategoriEkle("Rüyada Ağaç Görmek", "Hayat, köklenme ve büyümeyi temsil eder.", "Doğa");

            // ✅ İnsanlar
            db.altKategoriEkle("Rüyada Anne Görmek", "Koruma, şefkat ve rehberliği simgeler.", "İnsanlar");
            db.altKategoriEkle("Rüyada Baba Görmek", "Otorite, güç ve destek anlamına gelir.", "İnsanlar");
            db.altKategoriEkle("Rüyada Çocuk Görmek", "Masumiyet, umut ve yeni başlangıçları temsil eder.", "İnsanlar");
            db.altKategoriEkle("Rüyada Öğretmen Görmek", "Bilgelik, rehberlik ve öğrenme arzusudur.", "İnsanlar");
            db.altKategoriEkle("Rüyada Polis Görmek", "Kontrol, disiplin ve adalet isteğini ifade eder.", "İnsanlar");
            db.altKategoriEkle("Rüyada Doktor Görmek", "Şifa, yardım ve iyileşme arzusu simgesidir.", "İnsanlar");
            db.altKategoriEkle("Rüyada Yabancı Görmek", "Bilinmeyen taraflarınızı keşfetme sürecini simgeler.", "İnsanlar");
            db.altKategoriEkle("Rüyada Eski Sevgili Görmek", "Geçmişe özlem veya kapanmamış duygulara işarettir.", "İnsanlar");

            // ✅ Eşyalar
            db.altKategoriEkle("Rüyada Telefon Görmek", "İletişim kurma arzusu veya bir haber bekleniyor olabilir.", "Eşyalar");
            db.altKategoriEkle("Rüyada Elbise Görmek", "Kimlik, görünüm ve dışa yansıma ile ilgilidir.", "Eşyalar");
            db.altKategoriEkle("Rüyada Araba Görmek", "Kontrol, yön ve yaşam yolculuğunu simgeler.", "Eşyalar");
            db.altKategoriEkle("Rüyada Kitap Görmek", "Bilgi, keşif ve içsel gelişim anlamına gelir.", "Eşyalar");
            db.altKategoriEkle("Rüyada Ayakkabı Görmek", "Yolculuk, yön değiştirme ve aidiyet hissini temsil eder.", "Eşyalar");
            db.altKategoriEkle("Rüyada Saat Görmek", "Zaman, sabır ve farkındalığı simgeler.", "Eşyalar");
            db.altKategoriEkle("Rüyada Ayna Görmek", "Kendini tanıma ve içsel yansımayı ifade eder.", "Eşyalar");
            db.altKategoriEkle("Rüyada Cüzdan Görmek", "Değer, kimlik ve maddi kaygılara işarettir.", "Eşyalar");

            // ✅ Duygular
            db.altKategoriEkle("Rüyada Korkmak", "İçsel kaygı ve kontrol kaybını simgeler.", "Duygular");
            db.altKategoriEkle("Rüyada Mutlu Olmak", "Huzur, denge ve içsel tatmini gösterir.", "Duygular");
            db.altKategoriEkle("Rüyada Ağlamak", "Duygu boşalması, rahatlama ve içsel temizlenmedir.", "Duygular");
            db.altKategoriEkle("Rüyada Sevinmek", "Olumlu gelişmelere ve başarıya işarettir.", "Duygular");
            db.altKategoriEkle("Rüyada Öfkelenmek", "Bastırılmış duyguların dışa vurumudur.", "Duygular");
            db.altKategoriEkle("Rüyada Utanmak", "Kendini sorgulama veya gizlenen yönlerdir.", "Duygular");
            db.altKategoriEkle("Rüyada Endişelenmek", "Kararsızlık veya bir olay hakkında belirsizlik yaşandığını gösterir.", "Duygular");
            db.altKategoriEkle("Rüyada Aşık Olmak", "Bağlanma arzusu, tutkular ve isteklerdir.", "Duygular");

            // ✅ Yiyecekler
            db.altKategoriEkle("Rüyada Ekmek Görmek", "Bereket, temel ihtiyaçlar ve huzurlu yaşamı simgeler.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Su İçmek", "Ruhsal temizlik ve enerji yenilenmesi anlamına gelir.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Tatlı Görmek", "Keyifli olaylar, mutluluk ve sevgiye işarettir.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Et Görmek", "Güç, arzu ve içsel ihtiyaçları temsil eder.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Meyve Görmek", "Olgunluk, bolluk ve verimliliği simgeler.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Yemek Pişirmek", "Hazırlık, üretkenlik ve ilgiye işarettir.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Acıkmak", "Eksiklik hissi ve ruhsal doyumsuzluk olabilir.", "Yiyecekler");
            db.altKategoriEkle("Rüyada Sofra Görmek", "Paylaşım, aile ve birlikteliği temsil eder.", "Yiyecekler");

            // ✅ Mekanlar
            db.altKategoriEkle("Rüyada Ev Görmek", "Kendini, kişisel yaşam alanını simgeler.", "Mekanlar");
            db.altKategoriEkle("Rüyada Okul Görmek", "Öğrenme, gelişim ve geçmişe dönüş olabilir.", "Mekanlar");
            db.altKategoriEkle("Rüyada Camii Görmek", "Manevi arayış ve huzur isteğini temsil eder.", "Mekanlar");
            db.altKategoriEkle("Rüyada Hastane Görmek", "İyileşme ve içsel şifa arayışını gösterir.", "Mekanlar");
            db.altKategoriEkle("Rüyada Mezarlık Görmek", "Kapanışlar, kabulleniş ve geçmişle yüzleşme.", "Mekanlar");
            db.altKategoriEkle("Rüyada Park Görmek", "Rahatlama, doğa ve dinginliği simgeler.", "Mekanlar");
            db.altKategoriEkle("Rüyada Hapishane Görmek", "Kısıtlılık, baskı altında hissetme.", "Mekanlar");
            db.altKategoriEkle("Rüyada Yolda Olmak", "Hayat yolculuğu, yön ve kararları simgeler.", "Mekanlar");

            // 🔐 İlk veriler sadece bir kez eklensin
            sp.edit().putBoolean("eklendi", true).apply();
        }
    }
}
