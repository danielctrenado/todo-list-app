package nice.exceptions;

/**
 * Signals that todolist was not found.
 *
 * @author danielctrenado@gmail.com
 */
public class TodoListNotFoundException extends Exception {

    public TodoListNotFoundException() {
        super();
    }

    public TodoListNotFoundException(String message) {
        super(message);
    }

    public TodoListNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoListNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TodoListNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
