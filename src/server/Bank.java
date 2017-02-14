package server;

import exceptions.InvalidLogin;
import exceptions.InvalidSession;
import interfaces.BankInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public Bank() throws RemoteException
    {
        super();
        /** Create mock accounts to login with */
        accounts = new ArrayList<>();

        Account john = new Account("John1234", "1234");
        Account mary = new Account("Mary12", "12");
        Account joe = new Account("joe", "joe");

        accounts.add(john);
        accounts.add(joe);
        accounts.add(mary);
    }

    public void login(String username, String password) throws RemoteException, InvalidLogin {
        for (Account a: this.accounts) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)){
                this.loggedIn = true;
                this.currentUser = a;
                this.timeOfLogin = System.currentTimeMillis();
                System.out.println("User "+username+" has logged in. Time limit of 5 minutes before logout.");
                System.out.println("Account number is: "+a.getAccountNum());

            }
        }
        if (loggedIn != true){
            System.out.println("server.Account with username "+username+" does not exist or the password is incorrect.");
            throw new InvalidLogin("Username or password is incorrect.");
        }
    }

    @Override
    public void deposit(int accNum, int amount) throws RemoteException, InvalidSession {
        if (this.loggedIn && ((System.currentTimeMillis() - this.timeOfLogin) < loginPeriod)) {
            for (Account a : this.accounts) {
                if (a.getAccountNum() == accNum) {
                    a.updateBalance(amount);
                    a.addTransaction(new Transaction("Deposit", a.getBalance()));
                    System.out.println(amount + " deposited into account number " + accNum);
                    break;
                }
            }
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
                    break;
                }
            }
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
            System.out.println("server.Account number" + accNum + " does not exist.");
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
            System.setProperty("java.security.policy", "file:/Users/conor/IdeaProjects/DistributedBankingApplication/src/security.policy");
            System.setSecurityManager(new SecurityManager());
            System.out.println("Security manager set");
        }
        try {
            String registryName = "Accounts";
            BankInterface bankServer = new Bank();

            System.out.println("Instance of server.Bank Server created");
            // Put the server object into the Registry
            //Naming.rebind("Accounts", bankServer);
            /** Update registry so user can enter a port number */
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            registry.rebind(registryName, bankServer);
            System.out.println("Name rebind completed");
            System.out.println("Server ready for requests!");
        }
        catch(Exception e) {
            System.out.println("Error in main - ");
            e.printStackTrace();
        }
    }
}