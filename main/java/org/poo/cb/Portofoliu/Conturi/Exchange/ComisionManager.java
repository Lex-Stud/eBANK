package org.poo.cb.Portofoliu.Conturi.Exchange;
import java.util.Map;

public class ComisionManager {
    public Exchanege exchange;

    public void setExchange(Exchanege exchange) {
        this.exchange = exchange;
    }
    
    public double exchange(String from, String to, double amount,  Map <String, Map<String, Double>> rates) {
        return exchange.exchange(from, to, amount, rates);
    }
}