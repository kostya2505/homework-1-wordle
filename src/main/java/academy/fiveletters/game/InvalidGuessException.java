package academy.fiveletters.game;

public class InvalidGuessException extends RuntimeException {
    /**
     * Constructs a new InvalidGuessException with the specified message.
     *
     * @param message the error message
     */
    public InvalidGuessException(String message) {
        super(message);
    }
}
