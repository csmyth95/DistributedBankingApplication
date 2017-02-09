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

// CLIENT
/** When the server connection starts, need a 5 minute timer before its shutdown*/
public class ATM {

    public static void main (String args[]) throws Exception {
        // get user’s input, and perform the operations
        try {
            System.setSecurityManager(new RMISecurityManager());
            BankInterface bankServer = (BankInterface) Naming.lookup("//localhost/Accounts");

            // Get command line args
            String type = "Hello";
            switch (type) {
                case "login":

                    break;
                case "inquiry":
                    break;
                case "deposit":
                    break;
                case "withdraw":
                    break;
                case "statement":
                    break;
            }

            // get statement for account
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}