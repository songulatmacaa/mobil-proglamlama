package com.example.ruyatabiru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KullaniciDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "kullanicilar";
    private static final String COL_ID = "id";
    private static final String COL_ADSOYAD = "adSoyad";
    private static final String COL_EMAIL = "email";
    private static final String COL_SIFRE = "sifre";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Kullanıcı tablosunu oluşturuyoruz
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ADSOYAD + " TEXT, " +
                COL_EMAIL + " TEXT UNIQUE, " +
                COL_SIFRE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Veritabanı güncellenirse eski tabloyu sil ve yenisini oluştur
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Kullanıcı eklemek için metot
    public boolean kullaniciEkle(String adSoyad, String email, String sifre) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ADSOYAD, adSoyad);
        values.put(COL_EMAIL, email);
        values.put(COL_SIFRE, sifre);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Başarılıysa true döner
    }

    // Email ile kullanıcı var mı diye kontrol eder
    public boolean kullaniciVarMi(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COL_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean varMi = cursor.moveToFirst();

        cursor.close();
        db.close();

        return varMi;
    }

    // Email ve şifre ile giriş doğrulaması yapar
    public boolean kullaniciGirisKontrol(String email, String sifre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null,
                COL_EMAIL + "=? AND " + COL_SIFRE + "=?",
                new String[]{email, sifre}, null, null, null);

        boolean girisBasarili = cursor.moveToFirst();

        cursor.close();
        db.close();

        return girisBasarili;
    }
}
