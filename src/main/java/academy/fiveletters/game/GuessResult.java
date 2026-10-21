package academy.fiveletters.game;

import java.util.List;
import java.util.Objects;

/** Represents the result of a guess in a game session. */
public final class GuessResult {

    private final String guess;
    private final List<LetterStatus> statuses;

    /**
     * Constructs a new GuessResult with the specified guess, statuses, and game status.
     *
     * @param guess the guessed word
     * @param statuses the list of letter statuses indicating the result of each letter in the guess
     */
    GuessResult(String guess, List<LetterStatus> statuses) {
        this.guess = Objects.requireNonNull(guess, "guess");
        this.statuses = List.copyOf(Objects.requireNonNull(statuses, "statuses"));
    }

    /**
     * Returns the guessed word.
     *
     * @return the guessed word
     */
    public String guess() {
        return guess;
    }

    /**
     * Returns the list of letter statuses indicating the result of each letter in the guess.
     *
     * @return the list of letter statuses
     */
    public List<LetterStatus> statuses() {
        return statuses;
    }

    /**
     * Checks if the game session is won.
     *
     * @return true if the game session is won, false otherwise
     */
    public boolean isWin() {
        return statuses.stream().allMatch(s -> s == LetterStatus.CORRECT);
    }
}
