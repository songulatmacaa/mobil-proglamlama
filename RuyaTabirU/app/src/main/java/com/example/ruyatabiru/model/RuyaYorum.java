package com.example.ruyatabiru.model;

public class RuyaYorum {
    private int id;
    private String ruya;
    private String yorum;

    // 3 parametreli constructor
    public RuyaYorum(int id, String ruya, String yorum) {
        this.id = id;
        this.ruya = ruya;
        this.yorum = yorum;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuya() {
        return ruya;
    }

    public void setRuya(String ruya) {
        this.ruya = ruya;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }
}
