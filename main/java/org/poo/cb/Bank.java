package org.poo.cb;

import org.poo.cb.Utilizatori.Utilizator;
import org.poo.cb.Utilizatori.BuilderUser;
import java.util.*;
import java.io.*;

// Singleton pattern
public class Bank {
  private static Bank instance;
  private ArrayList < Utilizator > utilizatori = new ArrayList < > ();
  private BuilderUser builderUser = new BuilderUser();
  private Map < String, Map < String, Double >> exchange_rate = new HashMap < String, Map < String, Double >> ();
  private Map < String, ArrayList < Double >> stocks = new HashMap < String, ArrayList < Double >> ();
  private ArrayList < String > reduce_stocks = new ArrayList < String > ();

  private Bank() {}

  public static Bank getInstance() {
    synchronized(Bank.class) {

      if (instance == null) {
        instance = new Bank();
      }
    }
    return instance;
  }

  void clean() {
    utilizatori.clear();
    exchange_rate.clear();
    stocks.clear();
    reduce_stocks.clear();
  }

  public boolean existUser(String email) {
    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }

  public void addUser(String email, String prenume, String nume, String adresa) {
    if (existUser(email) == true) {
      System.out.println("User with " + email + " already exists");
      return;
    } else {
      Utilizator utilizator = builderUser.setEmail(email)
        .setNume(nume)
        .setPrenume(prenume)
        .setAddress(adresa)
        .build();

      utilizatori.add(utilizator);
    }
  }

  public void listUser(String email) {
    boolean found = false;
    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.listUser();
        found = true;
      }
    }

    if (!found) {
      System.out.println("User with " + email + " doesn't exist");
    }
  }

  public void addFriend(String email, String friendEmail) {
    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    if (existUser(friendEmail) == false) {
      System.out.println("User with " + friendEmail + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        if (utilizator.findFriend(friendEmail)) {
          System.out.println("User with " + friendEmail + " is already a friend");
          return;
        }

        utilizator.addFriend(friendEmail);
      }
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(friendEmail)) {
        utilizator.addFriend(email);
      }
    }
  }

  public void addACCOUNT(String email, String valuta) {

    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.AddCount(valuta);
      }
    }
  }

  public void addMoney(String email, String valuta, double suma) {
    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.addMony(valuta, suma);
      }
    }
  }

  public void removeSuma(String email, String valuta, double suma) {
    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.removeSum(valuta, suma);
      }
    }
  }

  public void read_exchange_rate(String exchange_path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(exchange_path))) {
      String[] valuta = reader.readLine().split(",");
      String line;

      while ((line = reader.readLine()) != null) {
        String[] valori = line.split(",");
        Map < String, Double > rates = new HashMap < > ();

        for (int i = 1; i < valori.length; i++) {
          rates.put(valuta[i], Double.parseDouble(valori[i]));
        }

        exchange_rate.put(valori[0], rates);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void exchangeMoney(String email, String from, String to, double suma, String exchane_path) {
    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    if (exchange_rate.isEmpty())
      read_exchange_rate(exchane_path);

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.exchangeMoney(from, to, suma, exchange_rate);
      }
    }
  }

  public void ShowExchangeRate() {
    for (String valuta: exchange_rate.keySet()) {
      System.out.println("Valuta: " + valuta);
      Map < String, Double > rates = exchange_rate.get(valuta);
      for (String currency: rates.keySet()) {
        double rate = rates.get(currency);
        System.out.println(currency + ": " + rate);
      }
      System.out.println();
    }
  }

  public void transferMoney(String from, String to, String valuta, double suma) {

    if (existUser(from) == false) {
      System.out.println("User with " + from + " doesn't exist");
      return;
    }

    if (existUser(to) == false) {
      System.out.println("User with " + to + " doesn't exist");
      return;
    }

    Utilizator from_user = null;
    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(from)) {
        from_user = utilizator;

        if (utilizator.findFriend(to) == false) {
          System.out.println("You are not allowed to transfer money to " + to);
          return;
        }
        if (utilizator.getSum(valuta) < suma) {
          System.out.println("Insufficient amount in account " + valuta + " for transfer");
          return;
        }
      }
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(to)) {
        utilizator.addMony(valuta, suma);
        from_user.removeSum(valuta, suma);
        return;
      }
    }
  }

  public void read_stocks(String stock_path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(stock_path))) {
      String line = reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] valori = line.split(",");
        ArrayList < Double > pret = new ArrayList < > ();

        for (int i = 1; i < valori.length; i++) {
          double price = Double.parseDouble(valori[i]);
          price = Math.round(price * 1000.0) / 1000.0;
          pret.add(price);
        }

        stocks.put(valori[0], pret);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void buyStocksPremium(Utilizator utilizator, String companie, int numar_actiuni) {
    double pret = stocks.get(companie).get(stocks.get(companie).size() - 1);

    if (utilizator.getSum("USD") < numar_actiuni * (pret - pret * 0.05)) {
      System.out.println("Insufficient amount in account for buying stock");
      return;
    }

    utilizator.addActiuni(companie, numar_actiuni);
    utilizator.removeSum("USD", numar_actiuni * (pret - pret * 0.05));
  }

  public void buyStocks(String email, String companie, int numar_actiuni, String stock_path) {

    if (stocks.isEmpty())
      read_stocks(stock_path);

    if (reduce_stocks.isEmpty())
      readRecommendStock();

    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        if (utilizator.getPremium() == true) {
          buyStocksPremium(utilizator, companie, numar_actiuni);
          return;
        }

        if (utilizator.getSum("USD") < numar_actiuni * stocks.get(companie).get(stocks.get(companie).size() - 1)) {
          System.out.println("Insufficient amount in account for buying stock");
          return;
        }
        utilizator.addActiuni(companie, numar_actiuni);
        utilizator.removeSum("USD", numar_actiuni * stocks.get(companie).get(stocks.get(companie).size() - 1));
      }
    }
  }

  public String readRecommendStock() {
    String stocks_list = "";
    double last_5 = 0;
    double last_10 = 0;

    for (String companie: stocks.keySet()) {
      ArrayList < Double > pret = stocks.get(companie);
      last_5 = 0;
      last_10 = 0;

      for (int i = pret.size() - 1; i >= pret.size() - 5; i--) {
        last_5 += pret.get(i);
      }

      for (int i = pret.size() - 1; i >= pret.size() - 10; i--) {
        last_10 += pret.get(i);
      }

      last_5 /= 5;
      last_10 /= 10;
      if (last_5 > last_10)
        stocks_list += "\"" + companie + "\",";
      reduce_stocks.add(companie);
    }
    stocks_list = stocks_list.substring(0, stocks_list.length() - 1);
    return stocks_list;
  }

  public void recommendStock(String path_stock) {

    if (stocks.isEmpty())
      read_stocks(path_stock);

    String stocks_list = "";

    System.out.print("{\"stocksToBuy\":[");

    stocks_list = readRecommendStock();
    System.out.print(stocks_list);
    System.out.println("]}");
  }
  
  public void listPORTFOLIO(String email) {
    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.listPORTFOLIO();
        return;
      }
    }
  }

  public void buyPremium(String email) {
    if (existUser(email) == false) {
      System.out.println("User with " + email + " doesn't exist");
      return;
    }

    for (Utilizator utilizator: utilizatori) {
      if (utilizator.getEmail().equals(email)) {
        utilizator.buyPremium();
        return;
      }
    }
  }
}