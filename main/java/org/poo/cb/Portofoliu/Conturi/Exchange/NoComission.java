package org.poo.cb.Portofoliu.Conturi.Exchange;
import java.util.Map;

public class NoComission implements Exchanege{
    public double exchange(String from, String to, double amount,  Map <String, Map<String, Double>> rates) {
        return amount * rates.get(to).get(from);
    }
}