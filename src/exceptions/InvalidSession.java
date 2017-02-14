package exceptions;

/**
 * Created by conor on 13/02/2017.
 */
public class InvalidSession extends Exception {
    public InvalidSession(String message) {
        super(message);
    }
}
