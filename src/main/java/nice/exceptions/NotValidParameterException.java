package nice.exceptions;

/**
 * Signals that parameter is not valid.
 *
 * @author danielctrenado@gmail.com
 */
public class NotValidParameterException extends Exception {

    public NotValidParameterException() {
        super();
    }

    public NotValidParameterException(String message) {
        super(message);
    }

    public NotValidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidParameterException(Throwable cause) {
        super(cause);
    }

    protected NotValidParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
