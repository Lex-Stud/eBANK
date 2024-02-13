package org.poo.cb.Portofoliu.Actiuni;

public class Actiuni {
    String companie;
    int numarActiuni;

    public Actiuni(String comanie, int numarActiuni) {
        this.companie = comanie;
        this.numarActiuni = numarActiuni;
    }

    public String getCompanie() {
        return this.companie;
    }

    public int getNumarActiuni() {
        return this.numarActiuni;
    }

    public void addActiuni(int numarActiuni) {
        this.numarActiuni += numarActiuni;
    }
    
}
