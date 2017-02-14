package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by conor on 27/01/2017.
 */
public class Account implements Serializable{
    private int accountNum;
    private int balance;
    private String username;
    private String password;
    private List<Transaction> transactions;
    private static int incrementer;

    public Account(String uname, String pass) {
        this.username = uname;
        this.password = pass;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.accountNum = incrementer;
        incrementer++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }

    public void updateBalance(int amount) {
        this.balance += amount;
    }

    public int getAccountNum() {
        return this.accountNum;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions(){
        return this.transactions;
    }

}
