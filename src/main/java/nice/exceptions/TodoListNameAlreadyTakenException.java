package nice.exceptions;

/**
 * Signals that already exists that username.
 *
 * @author danielctrenado@gmail.com
 */
public class TodoListNameAlreadyTakenException extends Exception {

    public TodoListNameAlreadyTakenException() {
        super();
    }

    public TodoListNameAlreadyTakenException(String message) {
        super(message);
    }

    public TodoListNameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoListNameAlreadyTakenException(Throwable cause) {
        super(cause);
    }

    protected TodoListNameAlreadyTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
