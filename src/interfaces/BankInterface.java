package interfaces;

import exceptions.InvalidLogin;
import exceptions.InvalidSession;
import server.Account;
import server.Statement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BankInterface extends Remote {

    public int login(String username, String password) throws RemoteException, InvalidLogin;

    public Boolean deposit(int accountNum, float amount) throws RemoteException, InvalidSession;

    public Boolean withdraw(int accountNum, float amount) throws RemoteException, InvalidSession;

    public int inquiry(int acc) throws RemoteException, InvalidSession;

    public StatementInterface getStatement(int accountNumber, String from, String to) throws RemoteException, InvalidSession;

    public List<Account> getAccounts() throws RemoteException;

}

