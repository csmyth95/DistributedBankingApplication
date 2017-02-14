package server;

import exceptions.InvalidLogin;
import exceptions.InvalidSession;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankInterface extends Remote {

    public void login(String username, String password) throws RemoteException, InvalidLogin;

    public void deposit(int accountNum, int amount) throws RemoteException, InvalidSession;

    public void withdraw(int accountNum, int amount) throws RemoteException, InvalidSession;

    public int inquiry(int acc) throws RemoteException, InvalidSession;

    public Statement getStatement(int accountNumber, String from, String to) throws RemoteException, InvalidSession;

}

