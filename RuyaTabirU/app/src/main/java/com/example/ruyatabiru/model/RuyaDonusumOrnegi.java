package com.example.ruyatabiru.model;

import java.util.ArrayList;
import java.util.List;

public class RuyaDonusumOrnegi {

    /**
     * String tipinde rüya metinlerini Ruya modeline dönüştürür.
     */
    public static List<Ruya> stringListesiniRuyaListesineDonustur(List<String> stringRuyalar) {
        List<Ruya> ruyaListesi = new ArrayList<>();
        if (stringRuyalar == null) return ruyaListesi;

        for (String metin : stringRuyalar) {
            Ruya ruya = new Ruya(0, metin, "");
            ruyaListesi.add(ruya);
        }
        return ruyaListesi;
    }

    /**
     * Ruya listesini RuyaYorum listesine dönüştürür.
     */
    public static List<RuyaYorum> ruyaListesiniRuyaYorumListesineDonustur(List<Ruya> ruyaListesi) {
        List<RuyaYorum> yorumListesi = new ArrayList<>();
        if (ruyaListesi == null) return yorumListesi;

        for (Ruya ruya : ruyaListesi) {
            RuyaYorum yorum = new RuyaYorum(ruya.getId(), ruya.getBaslik(), ruya.getAciklama());
            yorumListesi.add(yorum);
        }
        return yorumListesi;
    }

    // İstersen başka dönüşüm metotları ekleyebilirsin
}
