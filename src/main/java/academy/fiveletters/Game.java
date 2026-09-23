package academy.fiveletters;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * The Game class provides methods for starting a game session. It uses a dictionary to provide the answer and allows
 * for customization of the maximum number of attempts and seed for the random number generator.
 */
public final class Game {

    /** The default maximum number of attempts allowed in a game. */
    public static final int DEFAULT_MAX_ATTEMPTS = 6;

    /** Private constructor to prevent instantiation of this utility class. */
    private Game() {}

    /**
     * Starts a game session with the given dictionary, maximum number of attempts, and random seed.
     *
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
        return new GameSession(dictionary, answer, maxAttempts);
    }

    public static GuessResult applyGuess(GameSession session, String rawGuess) {
        Objects.requireNonNull(session, "session");

        if (session.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Игра завершена со статусом " + session.getStatus() + ", новые попытки не принимаются");
        }

        String guess = normalizeAndValidate(rawGuess, session.dictionary());
        List<LetterStatus> statuses = markLetters(session.getAnswer(), guess);

        session.addAttempt(guess);
        if (guess.equals(session.getAnswer())) {
            session.setStatus(GameStatus.WIN);
        } else if (session.getAttemptsUsed() >= session.getMaxAttempts()) {
            session.setStatus(GameStatus.LOSE);
        }

        return new GuessResult(guess, statuses, session.getStatus());
    }

    private static String normalizeAndValidate(String rawGuess, Dictionary dictionary) {
        if (rawGuess == null) {
            throw new InvalidGuessException("Ввод не может быть null");
        }
        String guess = rawGuess.toLowerCase(Locale.ROOT);
        if (guess.length() != Dictionary.WORD_LENGTH) {
            throw new InvalidGuessException(
                    "Ожидалось ровно " + Dictionary.WORD_LENGTH + " букв, получено " + guess.length());
        }
        for (int i = 0; i < guess.length(); i++) {
            if (!Character.isLetter(guess.charAt(i))) {
                throw new InvalidGuessException("Допустимы только буквы: \"" + rawGuess + "\"");
            }
        }
        if (!dictionary.contains(guess)) {
            throw new InvalidGuessException("Слова нет в словаре: \"" + guess + "\"");
        }
        return guess;
    }

    static List<LetterStatus> markLetters(String answer, String guess) {
        int n = Dictionary.WORD_LENGTH;
        LetterStatus[] result = new LetterStatus[n];
        boolean[] marked = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = LetterStatus.CORRECT;
                marked[i] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            if (result[i] != null) {
                continue;
            }
            char letter = guess.charAt(i);
            boolean found = false;
            for (int j = 0; j < n; j++) {
                if (!marked[j] && answer.charAt(j) == letter) {
                    result[i] = LetterStatus.PRESENT;
                    marked[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                result[i] = LetterStatus.ABSENT;
            }
        }

        return List.of(result);
    }

    /**
     * Starts a game session with the given dictionary and the default maximum number of attempts.
     *
     * @param dictionary The dictionary providing the answer word.
     * @return A new GameSession instance with the dictionary and the default maximum number of attempts.
     * @throws NullPointerException If the dictionary is null.
     */
    public static GameSession startGame(Dictionary dictionary) {
        return startGame(dictionary, DEFAULT_MAX_ATTEMPTS, new Random().nextLong());
    }
}
