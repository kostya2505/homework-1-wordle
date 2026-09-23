package academy.fiveletters.game;

public class InvalidGuessException extends RuntimeException {
    public InvalidGuessException(String message) {
        super(message);
    }
}
