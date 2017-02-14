package interfaces;

import exceptions.InvalidLogin;
import exceptions.InvalidSession;
import server.Account;
import server.Statement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BankInterface extends Remote {

    public void login(String username, String password) throws RemoteException, InvalidLogin;

    public void deposit(int accountNum, int amount) throws RemoteException, InvalidSession;

    public void withdraw(int accountNum, int amount) throws RemoteException, InvalidSession;

    public int inquiry(int acc) throws RemoteException, InvalidSession;

    public StatementInterface getStatement(int accountNumber, String from, String to) throws RemoteException, InvalidSession;

    public List<Account> getAccounts() throws RemoteException;

}

