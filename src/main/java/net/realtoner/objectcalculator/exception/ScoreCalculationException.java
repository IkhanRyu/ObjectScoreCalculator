package net.realtoner.objectcalculator.exception;

/**
 * @author RyuIkHan
 * @since 2016. 3. 21.
 */
public class ScoreCalculationException extends Exception{

    public ScoreCalculationException() {
    }

    public ScoreCalculationException(String message) {
        super(message);
    }

    public ScoreCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScoreCalculationException(Throwable cause) {
        super(cause);
    }

    public ScoreCalculationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
