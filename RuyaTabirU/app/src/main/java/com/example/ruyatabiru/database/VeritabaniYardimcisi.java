package com.example.ruyatabiru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ruyatabiru.model.AltKategori;

import java.util.ArrayList;
import java.util.List;

public class VeritabaniYardimcisi extends SQLiteOpenHelper {

    private static final String VERITABANI_ADI = "ruya_tabirleri.db";
    private static final int SURUM = 1;

    // 🗂 Favoriler tablosu
    private static final String TABLO_FAVORILER = "Favoriler";
    private static final String SUTUN_BASLIK = "baslik";
    private static final String SUTUN_ACIKLAMA = "aciklama";

    // 📄 Alt kategoriler tablosu
    private static final String TABLO_ALT_KATEGORILER = "AltKategoriler";
    private static final String SUTUN_KATEGORI = "kategori";

    // 👤 Kullanıcılar tablosu
    private static final String TABLO_KULLANICI = "kullanici";
    private static final String SUTUN_KULLANICI_ADI = "kullaniciadi";
    private static final String SUTUN_SIFRE = "sifre";

    public VeritabaniYardimcisi(Context context) {
        super(context, VERITABANI_ADI, null, SURUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabloları oluştur
        String sqlFavoriler = "CREATE TABLE " + TABLO_FAVORILER + " (" +
                SUTUN_BASLIK + " TEXT PRIMARY KEY, " +
                SUTUN_ACIKLAMA + " TEXT)";

        String sqlAltKategori = "CREATE TABLE " + TABLO_ALT_KATEGORILER + " (" +
                SUTUN_BASLIK + " TEXT PRIMARY KEY, " +
                SUTUN_ACIKLAMA + " TEXT, " +
                SUTUN_KATEGORI + " TEXT)";

        String sqlKullanici = "CREATE TABLE " + TABLO_KULLANICI + " (" +
                SUTUN_KULLANICI_ADI + " TEXT PRIMARY KEY, " +
                SUTUN_SIFRE + " TEXT)";

        db.execSQL(sqlFavoriler);
        db.execSQL(sqlAltKategori);
        db.execSQL(sqlKullanici);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int eskiSurum, int yeniSurum) {
        // Veritabanı güncellendiğinde eski tabloları sil
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_FAVORILER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_ALT_KATEGORILER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_KULLANICI);
        onCreate(db);
    }

    // ✅ FAVORİLER İŞLEMLERİ

    public void favoriEkle(String baslik, String aciklama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_BASLIK, baslik);
        values.put(SUTUN_ACIKLAMA, aciklama);
        db.insertWithOnConflict(TABLO_FAVORILER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void favoriSil(String baslik) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLO_FAVORILER, SUTUN_BASLIK + "=?", new String[]{baslik});
        db.close();
    }

    public boolean favoriMi(String baslik) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLO_FAVORILER, null, SUTUN_BASLIK + "=?", new String[]{baslik}, null, null, null);
        boolean sonuc = cursor.moveToFirst();
        cursor.close();
        db.close();
        return sonuc;
    }

    public List<AltKategori> tumFavorileriAl() {
        List<AltKategori> favoriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_FAVORILER, null);

        if (cursor.moveToFirst()) {
            do {
                String baslik = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_BASLIK));
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_ACIKLAMA));
                favoriler.add(new AltKategori(baslik, aciklama, true));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriler;
    }

    // ✅ ALT KATEGORİ İŞLEMLERİ

    public void altKategoriEkle(String baslik, String aciklama, String kategoriAdi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_BASLIK, baslik);
        values.put(SUTUN_ACIKLAMA, aciklama);
        values.put(SUTUN_KATEGORI, kategoriAdi);
        db.insertWithOnConflict(TABLO_ALT_KATEGORILER, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public List<AltKategori> altKategorileriGetir(String kategoriAdi) {
        List<AltKategori> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLO_ALT_KATEGORILER,
                null,
                SUTUN_KATEGORI + "=?",
                new String[]{kategoriAdi},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String baslik = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_BASLIK));
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_ACIKLAMA));
                boolean favori = favoriMi(baslik);
                liste.add(new AltKategori(baslik, aciklama, favori));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return liste;
    }

    // ✅ KULLANICI İŞLEMLERİ

    public boolean kullaniciEkle(String kullaniciAdi, String sifre) {
        if (kullaniciVarMi(kullaniciAdi)) return false; // aynı kullanıcı varsa ekleme
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_KULLANICI_ADI, kullaniciAdi);
        values.put(SUTUN_SIFRE, sifre);
        long sonuc = db.insert(TABLO_KULLANICI, null, values);
        db.close();
        return sonuc != -1;
    }

    public boolean kullaniciVarMi(String kullaniciAdi) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_KULLANICI + " WHERE " + SUTUN_KULLANICI_ADI + "=?", new String[]{kullaniciAdi});
        boolean varMi = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return varMi;
    }

    public boolean girisKontrol(String kullaniciAdi, String sifre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_KULLANICI + " WHERE " + SUTUN_KULLANICI_ADI + "=? AND " + SUTUN_SIFRE + "=?", new String[]{kullaniciAdi, sifre});
        boolean girisBasarili = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return girisBasarili;
    }
}
