package org.poo.cb.Portofoliu.Conturi;

public class Withdrawal implements ManagerCont {
 
    public void execute(Cont cont, double suma) {
        cont.retrage(suma);
    }
}