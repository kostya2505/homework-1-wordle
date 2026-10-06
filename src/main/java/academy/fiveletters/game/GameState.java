package academy.fiveletters.game;

public interface GameState {
    /**
     * Processes a guess and updates the session accordingly.
     *
     * @return the result of the guess.
     */
    GuessResult handleGuess(GameSession session, String guess);

    /** @return true if the game is over (won or lost). */
    boolean isGameOver();

    /** @return the GameStatus corresponding to this state. */
    GameStatus getStatus();
}
