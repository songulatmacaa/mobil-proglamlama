package com.example.ruyatabiru.model;

public class AltKategori {
    private String baslik;
    private String aciklama;
    private boolean favori;

    public AltKategori(String baslik, String aciklama, boolean favori) {
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.favori = favori;
    }

    public String getBaslik() { return baslik; }
    public String getAciklama() { return aciklama; }
    public boolean isFavori() { return favori; }
    public void setFavori(boolean favori) { this.favori = favori; }
}
