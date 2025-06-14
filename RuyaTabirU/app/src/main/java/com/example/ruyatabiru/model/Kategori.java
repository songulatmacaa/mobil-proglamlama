package com.example.ruyatabiru.model;

public class Kategori {
    private String ad;
    private int resimId;

    public Kategori(String ad, int resimId) {
        this.ad = ad;
        this.resimId = resimId;
    }

    public String getAd() {
        return ad;
    }

    public int getResimId() {
        return resimId;
    }

    // Adapter içinde çağırabilmen için eklendi ✅
    public int getIkon() {
        return resimId;
    }
}
