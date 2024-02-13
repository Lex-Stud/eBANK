package org.poo.cb.Portofoliu.Conturi.Exchange;
import java.util.Map;

public interface Exchanege {
    public double exchange(String from, String to, double amount,  Map <String, Map<String, Double>> rates);
}