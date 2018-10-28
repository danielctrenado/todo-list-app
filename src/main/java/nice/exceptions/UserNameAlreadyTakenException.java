package nice.exceptions;

/**
 * Signals that already exists that username.
 *
 * @author danielctrenado@gmail.com
 */
public class UserNameAlreadyTakenException extends Exception {

    public UserNameAlreadyTakenException() {
        super();
    }

    public UserNameAlreadyTakenException(String message) {
        super(message);
    }

    public UserNameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameAlreadyTakenException(Throwable cause) {
        super(cause);
    }

    protected UserNameAlreadyTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
