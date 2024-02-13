package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Running Main");
            return;
        } else {
            String exchange_path = "src/main/resources/" + args[0];
            String stock_path = "src/main/resources/" + args[1];
            String commands_path = "src/main/resources/" + args[2];

            try (BufferedReader commands = new BufferedReader(new FileReader(commands_path))) {
                BankFacade bankFacade = new BankFacade();
                bankFacade.initializeBank();

                String line;
                while ((line = commands.readLine()) != null) {
                    String[] parameters = line.split(" ");
                    bankFacade.processCommand(parameters, exchange_path, stock_path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
