package net.realtoner.objectcalculator.exception;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class InvalidScoreObjectException extends Exception{

    public InvalidScoreObjectException() {
    }

    public InvalidScoreObjectException(String message) {
        super(message);
    }

    public InvalidScoreObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidScoreObjectException(Throwable cause) {
        super(cause);
    }

    public InvalidScoreObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
