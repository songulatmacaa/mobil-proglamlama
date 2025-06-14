package com.example.ruyatabiru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ruyatabiru.model.RuyaYorum;

import java.util.ArrayList;
import java.util.List;

public class RuyaGecmisiDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ruya_gecmisi.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ruya_yorumlari";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RUYA = "ruya";
    public static final String COLUMN_YORUM = "yorum";

    public RuyaGecmisiDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RUYA + " TEXT NOT NULL, " +
                COLUMN_YORUM + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ✅ Asıl kayıt fonksiyonu
    public void ruyaEkle(String ruya, String yorum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RUYA, ruya);
        values.put(COLUMN_YORUM, yorum);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // ✅ Uyum için ek takma metod
    public void yorumEkle(String ruya, String yorum) {
        ruyaEkle(ruya, yorum);
    }

    // Tüm kayıtları getir
    public List<RuyaYorum> tumRuyalariGetir() {
        List<RuyaYorum> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String ruya = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUYA));
                String yorum = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YORUM));
                liste.add(new RuyaYorum(id, ruya, yorum));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return liste;
    }
}
