package academy.fiveletters;

import java.util.Objects;
import java.util.Random;

public class Game {

    public static final int DEFAULT_MAX_ATTEMPTS = 6;

    private Game() {}

    public static GameSession startGame(Dictionary dictionary, int maxAttempts, long seed) {
        Objects.requireNonNull(dictionary, "dictionary");
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts должен быть больше 0: " + maxAttempts);
        }
        Random random = new Random(seed);
        String answer = dictionary.words().get(random.nextInt(dictionary.size()));
        return new GameSession(answer, maxAttempts);
    }

    public static GameSession startGame(Dictionary dictionary) {
        return startGame(dictionary, DEFAULT_MAX_ATTEMPTS, new Random().nextLong());
    }
}
