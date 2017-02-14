
/*
* Server definition
*
* NOTES:
* The Bank server should be initialised with a number of test accounts that have various balances that can then
* be accessed by the ATM clients.

The bank server program has only one command line parameter “server_port”, which specifies the
port of rmiregistry. The default port of rmiregistry is 1099, but we may have to use other ports,
if 1099 has already been used by some other programs.

You will need to define suitable classes for Account and Transaction and also
provide a class that implements the Statement interface.

*/

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

//SERVER
public class Bank extends UnicastRemoteObject implements BankInterface {
    private Boolean loggedIn;
    private Account currentUser;
    private long timeOfLogin;
    private List<Account> accounts; // users accounts
    private static long loginPeriod = 300000; // 5 minutes in milliseconds

    public Bank() throws RemoteException
    {
        super();
    }

    public void login(String username, String password) throws RemoteException, InvalidLogin {
        for (Account a: this.accounts) {
            if (a.getUsername() == username && a.getPassword() == password){
                this.loggedIn = true;
                this.currentUser = a;
                this.timeOfLogin = System.currentTimeMillis();
            }
        }
        System.out.println("Account with username "+username+" does not exist or the password is incorrect.");
        throw new InvalidLogin("Username or password is incorrect.");
    }

    @Override
    public void deposit(int accNum, int amount) throws RemoteException, InvalidSession {
        if (this.loggedIn && ((System.currentTimeMillis() - this.timeOfLogin) < loginPeriod)) {
            for (Account a : this.accounts) {
                if (a.getAccountNum() == accNum) {
                    a.updateBalance(amount);
                    a.addTransaction(new Transaction("Deposit", a.getBalance()));
                    System.out.println(amount + " deposited into account number " + accNum);
                    exit(1);
                }
            }
            System.out.println("Account number " + accNum + " does not exist.");
            throw new InvalidSession("Account number does not exist");
        }
        else {
            throw new InvalidSession("Session has expired, please login again.");
        }

    }

    @Override
    public void withdraw(int accNum, int amount) throws RemoteException, InvalidSession {
        if (this.loggedIn && ((System.currentTimeMillis() - this.timeOfLogin) < loginPeriod)) {
            for (Account a : this.accounts) {
                if (a.getAccountNum() == accNum) {
                    a.updateBalance(amount);
                    a.addTransaction(new Transaction("Withdrawal", a.getBalance()));
                    System.out.println(amount + " withdrew from account " + accNum);
                    exit(1);
                }
            }
            System.out.println("Account number "+accNum+" does not exist.");
            exit(1);
        }
        else {
            throw new InvalidSession("Session has expired, please login again.");
        }
    }

    @Override
    public int inquiry(int accNum) throws RemoteException, InvalidSession {
        if (this.loggedIn && ((System.currentTimeMillis() - this.timeOfLogin) < loginPeriod)) {
            for (Account a: accounts){
                if(a.getAccountNum() == accNum) {
                    System.out.println("The current balance of account "+accNum+" is €"+a.getBalance());
                    return a.getBalance();
                }
            }
            System.out.println("Account number" + accNum + " does not exist.");
            return 0;
        }
        else {
            throw new InvalidSession("Session has expired, please login again.");
        }
    }

    @Override
    public Statement getStatement(int accNum, String from, String to) throws RemoteException, InvalidSession {
        if (this.loggedIn && ((System.currentTimeMillis() - this.timeOfLogin) < loginPeriod)) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date fromDate = df.parse(from);
                Date toDate = df.parse(to);
                for (Account acc: this.accounts) {
                    if (acc.getAccountNum() == accNum) {
                        Statement newStatement = new Statement(fromDate, toDate, acc);
                        System.out.println(newStatement.toString());
                        return newStatement;
                    }
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
            System.out.println("No transactions found for the period "+from+" - "+ to);
            return null;
        }
        else {
            throw new InvalidSession("Session has expired, please login again.");
        }
    }

    public Boolean getLoggedIn() {
        return this.loggedIn;
    }

    public Account getCurrentUser() {
        return this.currentUser;
    }

    public long getTimeOfLogin() {
        return this.timeOfLogin;
    }


    public static void main(String args[]) throws Exception {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            System.out.println("Security manager set");

            Bank bankServer = new Bank();
            System.out.println("Instance of Bank Server created");
            // Put the server object into the Registry
            Naming.rebind("Accounts", bankServer);
            System.out.println("Name rebind completed");
            System.out.println("Server ready for requests!");

            /** Create mock accounts to login with */
            Account john = new Account("John1234", "1234");
            Account mary = new Account("Mary12", "12");
            Account joe = new Account("Joe34", "34");

            bankServer.accounts.add(john);
            bankServer.accounts.add(joe);
            bankServer.accounts.add(mary);
        }
        catch(Exception e) {
            System.out.println("Error in main - " + e.toString());
        }


    }

}