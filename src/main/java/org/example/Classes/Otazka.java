package org.example.Classes;

import java.util.List;

public class Otazka {
    private String text;
    private List<String> moznosti;
    private String spravnaOdpoved;
    private int body;

    public Otazka(String text, List<String> moznosti, String spravnaOdpoved, int body) {
        this.text = text;
        this.moznosti = moznosti;
        this.spravnaOdpoved = spravnaOdpoved;
        this.body = body;
    }

    // Getters
    public String getText() {
        return text;
    } // getter získá text, setter píše data...

    public List<String> getMoznosti() {
        return moznosti;
    }

    public String getSpravnaOdpoved() {
        return spravnaOdpoved;
    }

    public int getBody() {
        return body;
    }

    public boolean jeSpravne(String odpovedUzivatele) {
        return spravnaOdpoved.equalsIgnoreCase(odpovedUzivatele);
    }
}
