package academy.fiveletters.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Represents a game session with a specific answer, maximum attempts, and history of attempts. */
public class GameSession {

    private final Dictionary dictionary;

    /** The answer to be guessed in the game. */
    private final String answer;

    /** The maximum number of attempts allowed in the game. */
    private final int maxAttempts;

    /** A history of attempts made in the game. */
    private final List<String> attemptsHistory = new ArrayList<>();

    /** The current state of the game. */
    private GameState state = new InProgressState();

    /**
     * Constructs a new game session with the given answer and maximum attempts.
     *
     * @param answer the answer to be guessed
     * @param maxAttempts the maximum number of attempts allowed
     * @throws IllegalArgumentException if the answer is null or does not have the correct length
     * @throws IllegalArgumentException if the maximum attempts is less than 1
     */
    public GameSession(Dictionary dictionary, String answer, int maxAttempts) {
        this.dictionary = Objects.requireNonNull(dictionary, "dictionary");
        Objects.requireNonNull(answer, "answer");
        if (answer.length() != Dictionary.WORD_LENGTH) {
            throw new IllegalArgumentException("Слова должны иметь только " + Dictionary.WORD_LENGTH
                    + " букв, получено: " + answer.length() + " букв");
        }
        if (!dictionary.contains(answer)) {
            throw new IllegalArgumentException("Загаданное слово отсутствует в словаре: \"" + answer + "\"");
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
    public String answer() {
        return answer;
    }

    /**
     * Returns the maximum number of attempts allowed in the game.
     *
     * @return the maximum number of attempts
     */
    public int maxAttempts() {
        return maxAttempts;
    }

    /**
     * Returns the number of attempts already used in the game.
     *
     * @return the number of attempts used
     */
    public int attemptsUsed() {
        return attemptsHistory.size();
    }

    /**
     * Returns an unmodifiable list of attempts made in the game.
     *
     * @return an unmodifiable list of attempts
     */
    public List<String> attemptsHistory() {
        return Collections.unmodifiableList(attemptsHistory);
    }

    /**
     * Returns the current state of the game.
     *
     * @return the game state
     */
    public GameState state() {
        return state;
    }

    /**
     * Returns the current status of the game.
     *
     * @return the game status
     */
    public GameStatus status() {
        return state.getStatus();
    }

    Dictionary dictionary() {
        return dictionary;
    }

    /**
     * Adds a new attempt to the game session.
     *
     * @param attempt the attempt to add
     * @throws IllegalStateException if the game session is not in progress
     */
    void addAttempt(String attempt) {
        if (state.isGameOver()) {
            throw new IllegalStateException("Игра уже завершена со статусом: " + state.getStatus());
        }
        attemptsHistory.add(attempt);
    }

    /**
     * Sets a new state for the game session.
     *
     * @param newState the new state to set
     * @throws IllegalArgumentException if the new state is null
     */
    void setState(GameState newState) {
        this.state = Objects.requireNonNull(newState, "newState");
    }
}
