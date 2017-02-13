/*
* NOTES:
* The command line parameters of the ATM client application will include:
    server_address: the address of the rmiregistry
    server_port: the port of the rmiregistry
    operation: one of "login", "deposit", "withdraw", and "inquiry"
    account: the user account
    username: only for "login" operation
    password: only for "login" operation
    amount: only for “deposit” and “withdraw” operations
* */


import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// CLIENT
public class ATM {

    public static void main (String args[]) throws Exception {
        if (System.getSecurityManager() == null) {
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