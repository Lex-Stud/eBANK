package org.poo.cb.Portofoliu.Conturi;

public class Cont {
    private String valuta;
    private double suma;
    
    public Cont(String valuta) {
        this.valuta = valuta;
        this.suma = 0;
    }

    public String getValuta() {
        return this.valuta;
    }

    public double getSum() {
        return this.suma;
    }

    public void depune(double suma) {
        this.suma += suma;
    }

    public void retrage(double suma) {
        this.suma -= suma;
    }
}