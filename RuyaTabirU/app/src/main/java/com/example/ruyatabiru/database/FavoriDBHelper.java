package com.example.ruyatabiru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ruyatabiru.model.AltKategori;

import java.util.ArrayList;
import java.util.List;

public class FavoriDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Favoriler.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "favoriler";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BASLIK = "baslik";
    private static final String COLUMN_ACIKLAMA = "aciklama";

    public FavoriDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BASLIK + " TEXT, " +  // UNIQUE kaldırıldı, çünkü açıklama da var
                COLUMN_ACIKLAMA + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Favori ekle (aynı başlık ve açıklama yoksa ekle)
    public void favoriEkle(String baslik, String aciklama) {
        if (!favorideMi(baslik, aciklama)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_BASLIK, baslik);
            values.put(COLUMN_ACIKLAMA, aciklama);
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    // Favori sil (hem başlığa hem açıklamaya göre)
    public void favoridenCikar(String baslik, String aciklama) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_BASLIK + " = ? AND " + COLUMN_ACIKLAMA + " = ?", new String[]{baslik, aciklama});
        db.close();
    }

    // Belirli bir rüya favoride var mı kontrol et (başlık ve açıklama birlikte)
    public boolean favorideMi(String baslik, String aciklama) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_BASLIK + " = ? AND " + COLUMN_ACIKLAMA + " = ?",
                new String[]{baslik, aciklama}, null, null, null);
        boolean varMi = cursor.moveToFirst();
        cursor.close();
        db.close();
        return varMi;
    }

    // Tüm favorileri Cursor olarak getir (listelemede kullanılabilir)
    public Cursor tumFavorileriGetir() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");
    }

    // Tüm favorileri AltKategori listesi olarak getir (RecyclerView için)
    public List<AltKategori> tumFavorileriAl() {
        List<AltKategori> favoriList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                String baslik = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BASLIK));
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACIKLAMA));
                favoriList.add(new AltKategori(baslik, aciklama, true));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriList;
    }
}
