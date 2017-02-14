package client;

import interfaces.BankInterface;
import java.rmi.Naming;

// CLIENT
public class ATM {

    public static void main (String args[]) throws Exception {
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "file:/Users/conor/IdeaProjects/DistributedBankingApplication/src/security.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String hostname = args[0];
            String portNumber = args[1];
            BankInterface bankServer = (BankInterface) Naming.lookup("//"+hostname+"/Accounts");
            // Get command line args
            String type = args[2];
            int accountNumber;
            int amount;

            switch (type) {
                case "login":
                    System.out.println("Accounts: "+bankServer.getAccounts());
                    System.out.println("args[3]: "+args[3]+"args[4]: "+args[4]);
                    String username = args[3];
                    String password = args[4];
                    bankServer.login(username, password);
                    break;
                case "inquiry":
                    accountNumber = Integer.parseInt(args[3]);
                    bankServer.inquiry(accountNumber);
                    break;
                case "deposit":
                    accountNumber = Integer.parseInt(args[3]);
                    amount = Integer.parseInt(args[4]);
                    bankServer.deposit(accountNumber, amount);
                    break;
                case "withdraw":
                    accountNumber = Integer.parseInt(args[3]);
                    amount = Integer.parseInt(args[4]);
                    bankServer.withdraw(accountNumber, amount);
                    break;
                case "statement":
                    accountNumber = Integer.parseInt(args[3]);
                    String to = args[4];
                    String from = args[5];
                    bankServer.getStatement(accountNumber, to, from);
                    break;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}