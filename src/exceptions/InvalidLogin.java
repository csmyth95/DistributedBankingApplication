package exceptions;

/**
 * Created by conor on 13/02/2017.
 */
public class InvalidLogin extends Exception {
    public InvalidLogin(String message) {
        super(message);
    }
}
