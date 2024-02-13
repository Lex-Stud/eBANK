package org.poo.cb.Portofoliu.Conturi;

public class AddSum implements ManagerCont {
    
    public void execute(Cont cont, double suma) {
        cont.depune(suma);
    }
}