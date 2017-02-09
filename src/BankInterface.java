import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface BankInterface extends Remote {

    public long login(String username, String password) throws RemoteException, InvalidLogin;

    public void deposit(int accountNum, int amount, long sessionID) throws RemoteException, InvalidSession;

    public void withdraw(int accountNum, int amount, long sessionID) throws RemoteException, InvalidSession;

    public int inquiry(int acc, long sessionID) throws RemoteException, InvalidSession;

    public Statement getStatement(int accountNumber, String from, String to, long sessionID) throws RemoteException, InvalidSession;

}

class InvalidLogin extends Exception {
    public InvalidLogin(String message) {
        super(message);
    }
}

class InvalidSession extends Exception {
    public InvalidSession(String message) {
        super(message);
    }
}

