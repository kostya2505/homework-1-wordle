package academy.fiveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Represents a game session with a specific answer, maximum attempts, and history of attempts. */
public class GameSession {

    /** The answer to be guessed in the game. */
    private final String answer;

    /** The maximum number of attempts allowed in the game. */
    private final int maxAttempts;

    /** A history of attempts made in the game. */
    private final List<String> attemptsHistory = new ArrayList<>();

    /** The current status of the game. */
    private GameStatus status = GameStatus.IN_PROGRESS;

    /**
     * Constructs a new game session with the given answer and maximum attempts.
     *
     * @param answer the answer to be guessed
     * @param maxAttempts the maximum number of attempts allowed
     * @throws IllegalArgumentException if the answer is null or does not have the correct length
     * @throws IllegalArgumentException if the maximum attempts is less than 1
     */
    public GameSession(String answer, int maxAttempts) {
        Objects.requireNonNull(answer, "answer");
        if (answer.length() != Dictionary.WORD_LENGTH) {
            throw new IllegalArgumentException("Слова должны иметь только " + Dictionary.WORD_LENGTH
                    + " букв, получено: " + answer.length() + " букв");
        }
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Максимальное количество попыток должно быть больше 0: " + maxAttempts);
        }
        this.answer = answer;
        this.maxAttempts = maxAttempts;
    }

    /**
     * Returns the answer to be guessed in the game.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns the maximum number of attempts allowed in the game.
     *
     * @return the maximum number of attempts
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Returns the number of attempts already used in the game.
     *
     * @return the number of attempts used
     */
    public int getAttemptsUsed() {
        return attemptsHistory.size();
    }

    /**
     * Returns an unmodifiable list of attempts made in the game.
     *
     * @return an unmodifiable list of attempts
     */
    public List<String> getAttemptsHistory() {
        return Collections.unmodifiableList(attemptsHistory);
    }

    /**
     * Returns the current status of the game.
     *
     * @return the game status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Adds a new attempt to the game session.
     *
     * @param attempt the attempt to add
     * @throws IllegalStateException if the game session is not in progress
     */
    void addAttempt(String attempt) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Игра уже завершена со статусом: " + status);
        }
        attemptsHistory.add(attempt);
    }

    /**
     * Sets a new status for the game session.
     *
     * @param newStatus the new status to set
     * @throws IllegalArgumentException if the new status is null
     */
    void setStatus(GameStatus newStatus) {
        this.status = Objects.requireNonNull(newStatus, "newStatus");
    }
}
