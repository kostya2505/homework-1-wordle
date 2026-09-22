package academy.fiveletters;

import java.util.Objects;
import java.util.Random;

/**
 * The Game class provides methods for starting a game session.
 * It uses a dictionary to provide the answer and allows for customization of the maximum number of attempts and seed for the random number generator.
 */
public class Game {

    /**
     * The default maximum number of attempts allowed in a game.
     */
    public static final int DEFAULT_MAX_ATTEMPTS = 6;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Game() {}

    /**
     * Starts a game session with the given dictionary, maximum number of attempts, and random seed.
     * @param dictionary The dictionary providing the answer word.
     * @param maxAttempts The maximum number of attempts allowed in the game.
     * @param seed The seed for the random number generator to ensure reproducibility.
     * @return A new GameSession instance with the provided parameters.
     * @throws IllegalArgumentException If the maxAttempts is less than or equal to 0.
     * @throws NullPointerException If the dictionary is null.
     */
    public static GameSession startGame(Dictionary dictionary, int maxAttempts, long seed) {
        Objects.requireNonNull(dictionary, "dictionary");
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts должен быть больше 0: " + maxAttempts);
        }
        Random random = new Random(seed);
        String answer = dictionary.words().get(random.nextInt(dictionary.size()));
        return new GameSession(answer, maxAttempts);
    }

    /**
     * Starts a game session with the given dictionary and the default maximum number of attempts.
     * @param dictionary The dictionary providing the answer word.
     * @return A new GameSession instance with the dictionary and the default maximum number of attempts.
     * @throws NullPointerException If the dictionary is null.
     */
    public static GameSession startGame(Dictionary dictionary) {
        return startGame(dictionary, DEFAULT_MAX_ATTEMPTS, new Random().nextLong());
    }
}
