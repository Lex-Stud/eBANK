package org.poo.cb.Utilizatori;
import java.util.ArrayList;
import java.util.Map;

import org.poo.cb.Portofoliu.Actiuni.Actiuni;
import org.poo.cb.Portofoliu.Conturi.AddSum;
import org.poo.cb.Portofoliu.Conturi.Cont;
import org.poo.cb.Portofoliu.Conturi.Exchange.ComisionManager;
import org.poo.cb.Portofoliu.Conturi.Exchange.Commision;
import org.poo.cb.Portofoliu.Conturi.Exchange.Exchanege;
import org.poo.cb.Portofoliu.Conturi.Exchange.NoComission;
import org.poo.cb.Portofoliu.Conturi.ManagerCont;
import org.poo.cb.Portofoliu.Conturi.Withdrawal;

public class Utilizator {
  boolean premium = false;
  private String email;
  private String nume, prenume;
  private String adresa;
  private ArrayList < String > friends = new ArrayList < String > ();
  private ArrayList < Cont > conturi = new ArrayList < Cont > ();
  private ArrayList < Actiuni > actiuni = new ArrayList < Actiuni > ();
  
  // Constructor poate fi aplat doar in pachetul Utilizatori
  Utilizator(String email, String nume, String prenume, String adresa) {
    this.email = email;
    this.nume = nume;
    this.prenume = prenume;
    this.adresa = adresa;
  }

  public String getEmail() {
    return this.email;
  }

  public boolean getPremium() {
    return this.premium;
  }

  public void addFriend(String email) {
    this.friends.add(email);
  }

  public void stergePrieten(String email) {
    this.friends.remove(email);
  }

  public void listUser() {
    System.out.print("{");
    System.out.print("\"email\":\"" + this.email + "\",");
    System.out.print("\"firstname\":\"" + this.prenume + "\",");
    System.out.print("\"lastname\":\"" + this.nume + "\",");
    System.out.print("\"address\":\"" + this.adresa + "\",");
    System.out.print("\"friends\":[");

    for (int i = 0; i < this.friends.size(); i++) {
      System.out.print("\"" + this.friends.get(i) + "\"");
      if (i != this.friends.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.print("]");
    System.out.println("}");
  }

  public boolean findFriend(String email) {
    if (this.friends.contains(email)) {
      return true;
    } else {
      return false;
    }
  }

  public void AddCount(String valuta) {
    for (Cont cont: this.conturi) {
      if (cont.getValuta().equals(valuta)) {
        System.out.println("The account with currency " + valuta + " already exists");
        return;
      }
    }
    Cont cont = new Cont(valuta);
    this.conturi.add(cont);
  }

  public double getSum(String valuta) {
    for (Cont cont: this.conturi) {
      if (cont.getValuta().equals(valuta)) {
        return cont.getSum();
      }
    }
    return 0;
  }

  public void addMony(String valuta, double suma) {
    ManagerCont addSuma = new AddSum();
    for (Cont cont: this.conturi) {
      if (cont.getValuta().equals(valuta)) {
        addSuma.execute(cont, suma);
      }
    }

  }

  public void removeSum(String valuta, double suma) {
    ManagerCont withdrawal = new Withdrawal(); 
    for (Cont cont: this.conturi) {
      if (cont.getValuta().equals(valuta)) {
        withdrawal.execute(cont, suma);
      }
    }
  }

  public void exchangeMoney(String from, String to, double suma, Map < String, Map < String, Double >> curs) {
    ComisionManager comisionManager = new ComisionManager();
    Exchanege commission = new Commision();
    Exchanege noCommission = new NoComission();
    double suma_de_extras = curs.get(to).get(from) * suma;
    ManagerCont withdrawal = new Withdrawal();
    ManagerCont addSuma = new AddSum();
    
    for (Cont cont: this.conturi) {
      if (cont.getValuta().equals(from)) {
        if (cont.getSum() < suma_de_extras) {
          System.out.println("Insufficient amount in account " + from + " for exchange");
          return;
        }
        for (Cont cont2: this.conturi) {
          if (cont2.getValuta().equals(to)) {

            if (suma_de_extras > cont.getSum() / 2)
              comisionManager.setExchange(commission);
            else
              comisionManager.setExchange(noCommission);

            if (premium == true)
              comisionManager.setExchange(noCommission);

            suma_de_extras = comisionManager.exchange(from, to, suma, curs);

            withdrawal.execute(cont, suma_de_extras);
            addSuma.execute(cont2, suma);
          }
        }
      }
    }
  }

  public void addActiuni(String comapnie, int numar_actiuni) {

    for (Actiuni actiune: this.actiuni) {
      if (actiune.getCompanie().equals(comapnie)) {
        actiune.addActiuni(numar_actiuni);
        return;
      }
    }

    actiuni.add(new Actiuni(comapnie, numar_actiuni));
  }

  public void listPORTFOLIO() {
    System.out.print("{\"stocks\":[");

    for (int i = 0; i < this.actiuni.size(); i++) {
      System.out.print("{\"stockName\":\"" + this.actiuni.get(i).getCompanie() + "\",");
      System.out.print("\"amount\":" + this.actiuni.get(i).getNumarActiuni() + "}");
      if (i != this.actiuni.size() - 1) {
        System.out.print(",");
      }
    }
    System.out.print("],\"accounts\":[{");

    for (int i = 0; i < this.conturi.size(); i++) {
      System.out.print("\"currencyName\":\"" + this.conturi.get(i).getValuta() + "\",");
      System.out.printf("\"amount\":\"%.2f\"", this.conturi.get(i).getSum());
      if (i != this.conturi.size() - 1) {
        System.out.print("},{");
      }
    }
    System.out.print("}]}\n");
  }

  public void buyPremium() {
    ManagerCont withdrawal = new Withdrawal();

    for (Cont cont: this.conturi) {
      if (cont.getValuta().equals("USD"))
        if (cont.getSum() < 100) {
          System.out.println("Insufficient amount in account for buying premium option");
          return;
        }
    }

    if (this.premium == true) {
      System.out.println("User " + this.email + " is already premium");
    } else {

      for (Cont cont: this.conturi) {
        if (cont.getValuta().equals("USD")) {
          withdrawal.execute(cont, 100);
        }
      }
      this.premium = true;
    }
  }
}