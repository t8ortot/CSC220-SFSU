package FC;

/**
 * Used by the SimpleFraction class to throw errors with messages
 *
 * @author James Clark
 */
public class SimpleFractionException extends RuntimeException {
    /**
     * Throws error with no message
     */
    public SimpleFractionException() {
        this("");
    }

    /**
     * Throws error with message
     *
     * @param errorMsg error message to be returned
     */
    public SimpleFractionException(String errorMsg) {
        super(errorMsg);
    }

}
