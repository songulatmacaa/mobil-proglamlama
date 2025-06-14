package com.example.ruyatabiru.model;

public class Ruya {
    private int id;
    private String baslik;
    private String aciklama;

    public Ruya(int id, String baslik, String aciklama) {
        this.id = id;
        this.baslik = baslik;
        this.aciklama = aciklama;
    }

    public int getId() {
        return id;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    // İstersen setter metodları ekleyebilirsin
    public void setId(int id) {
        this.id = id;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
