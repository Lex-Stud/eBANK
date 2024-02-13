package org.poo.cb;

public class BankFacade {
    private Bank bank;

    public void initializeBank() {
        this.bank = Bank.getInstance();
        bank.clean();
    }

    public void processCommand(String[] parameters, String exchangePath, String stockPath) {
        if (parameters[0].equals("CREATE") && parameters[1].equals("USER")) {
            StringBuilder address = new StringBuilder();
            for (int i = 5; i < parameters.length; i++) {
                address.append(parameters[i]);

                if (i != parameters.length - 1) {
                    address.append(" ");
                }
            }

            bank.addUser(parameters[2], parameters[3], parameters[4], address.toString());
        } else if (parameters[0].equals("ADD") && parameters[1].equals("FRIEND")) {
            if (parameters[2] == null || parameters[3] == null) {
                System.out.println("Error ADD FRIEND parameters[2] or parameters[3] is null");
            }
            bank.addFriend(parameters[2], parameters[3]);
        } else if (parameters[0].equals("LIST") && parameters[1].equals("USER")) {
            bank.listUser(parameters[2]);
        } else if (parameters[0].equals("ADD") && parameters[1].equals("ACCOUNT")) {
            if (parameters[2] == null || parameters[3] == null) {
                System.out.println("Error ADD PORTFOLIO parameters[2] or parameters[3] or parameters[4] is null");
            }
            bank.addACCOUNT(parameters[2], parameters[3]);
        } else if (parameters[0].equals("ADD") && parameters[1].equals("MONEY")) {
            if (parameters[2] == null || parameters[3] == null) {
                System.out.println("Error ADD MONEY parameters[2] or parameters[3] or parameters[4] is null");
            }
            bank.addMoney(parameters[2], parameters[3], Double.parseDouble(parameters[4]));
        } else if (parameters[0].equals("EXCHANGE") && parameters[1].equals("MONEY")) {
            if (parameters[2] == null || parameters[3] == null) {
                System.out.println("Error EXCHANGE MONEY parameters[2] or parameters[3] or parameters[4] is null");
            }
            bank.exchangeMoney(parameters[2], parameters[3], parameters[4], Double.parseDouble(parameters[5]), exchangePath);
        } else if (parameters[0].equals("TRANSFER") && parameters[1].equals("MONEY")) {
            if (parameters[2] == null || parameters[3] == null || parameters[4] == null || parameters[5] == null) {
                System.out.println("Error TRANSFER MONEY parameters[2] or parameters[3] or parameters[4] is null");
            }
            bank.transferMoney(parameters[2], parameters[3], parameters[4], Double.parseDouble(parameters[5]));
        } else if (parameters[0].equals("BUY") && parameters[1].equals("STOCKS")) {
            if (parameters[2] == null || parameters[3] == null || parameters[4] == null) {
                System.out.println("Error BUY STOCKS parameters[2] or parameters[3] or parameters[4] is null");
            }
            bank.buyStocks(parameters[2], parameters[3], Integer.parseInt(parameters[4]), stockPath);
        } else if (parameters[0].equals("RECOMMEND") && parameters[1].equals("STOCKS")) {
            bank.recommendStock(stockPath);
        } else if (parameters[0].equals("LIST") && parameters[1].equals("PORTFOLIO")) {
            if (parameters[2] == null) {
                System.out.println("Error LIST PORTFOLIO parameters[2] is null");
            }
            bank.listPORTFOLIO(parameters[2]);
        } else if (parameters[0].equals("BUY") && parameters[1].equals("PREMIUM")) {
            if (parameters[2] == null) {
                System.out.println("Error BUY PREMIUM parameters[2] is null");
            }
            bank.buyPremium(parameters[2]);
        }
    }
}
