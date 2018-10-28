package nice.exceptions;

/**
 * Signals that already exists that username.
 *
 * @author danielctrenado@gmail.com
 */
public class TaskNameAlreadyTakenException extends Exception {

    public TaskNameAlreadyTakenException() {
        super();
    }

    public TaskNameAlreadyTakenException(String message) {
        super(message);
    }

    public TaskNameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNameAlreadyTakenException(Throwable cause) {
        super(cause);
    }

    protected TaskNameAlreadyTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
