package org.poo.cb.Utilizatori;
/*
 * Builder pattern
 * AM folosit acest pattern pentru a putea crea un obiect de tip Utilizator
 * fara a fi nevoit sa ii dau toate campurile in constructor.
 * Cazuri posibile de admin in care nu are nevoie de toate campurile:
 * Persone care nu au adresa cunoscuta etc.
 */

public class BuilderUser {
    private String email;
    private String nume, prenume;
    private String adresa;

    public BuilderUser setEmail(String email) {
        this.email = email;
        return this;
    }
    
    public BuilderUser setNume(String nume) {
        this.nume = nume;
        return this;
    }

    public BuilderUser setPrenume(String prenume) {
        this.prenume = prenume;
        return this;
    }

    public BuilderUser setAddress(String adresa) {
        this.adresa = adresa;
        return this;
    }

    public Utilizator build() {
        return new Utilizator(this.email, this.nume, this.prenume, this.adresa);
    }
}
