package academy.fiveletters.game;

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

    /**
     * Applies a guess to the current game session.
     *
     * @param session The current game session.
     * @param guess The user's raw guess.
     * @return A GuessResult object containing the result of the guess.
     */
    public static GuessResult applyGuess(GameSession session, String guess) {
        return session.state().handleGuess(session, guess);
    }

    static GuessResult processGuess(GameSession session, String guess) {
        String normalized = normalizeAndValidate(guess, session.dictionary());
        session.addAttempt(normalized);
        List<LetterStatus> statuses = markLetters(session.answer(), normalized);
        return new GuessResult(normalized, statuses);
    }

    /**
     * Normalizes and validates the user's guess.
     *
     * @param rawGuess The user's raw guess.
     * @param dictionary The dictionary to validate against.
     * @return The normalized guess.
     * @throws InvalidGuessException If the guess is invalid.
     */
    private static String normalizeAndValidate(String rawGuess, Dictionary dictionary) {
        if (rawGuess == null) {
            throw new InvalidGuessException("Ввод не может быть null");
        }
        String guess = rawGuess.toLowerCase(Locale.ROOT);
        validateLength(guess);
        validateLetters(guess, rawGuess);
        validateInDictionary(guess, dictionary);
        return guess;
    }

    /**
     * Validates the length of the guess.
     *
     * @param guess The guess to validate.
     * @throws InvalidGuessException If the length is invalid.
     */
    private static void validateLength(String guess) {
        if (guess.length() != Dictionary.WORD_LENGTH) {
            throw new InvalidGuessException(
                    "Ожидалось ровно " + Dictionary.WORD_LENGTH + " букв, получено " + guess.length());
        }
    }

    /**
     * Validates the letters in the guess.
     *
     * @param guess The guess to validate.
     * @param rawGuess The raw user input.
     * @throws InvalidGuessException If the letters are invalid.
     */
    private static void validateLetters(String guess, String rawGuess) {
        for (int i = 0; i < guess.length(); i++) {
            if (!Character.isLetter(guess.charAt(i))) {
                throw new InvalidGuessException("Допустимы только буквы: \"" + rawGuess + "\"");
            }
        }
    }

    /**
     * Validates that the guess is in the dictionary.
     *
     * @param guess The guess to validate.
     * @param dictionary The dictionary to check against.
     * @throws InvalidGuessException If the guess is not in the dictionary.
     */
    private static void validateInDictionary(String guess, Dictionary dictionary) {
        if (!dictionary.contains(guess)) {
            throw new InvalidGuessException("Слова нет в словаре: \"" + guess + "\"");
        }
    }

    /**
     * Marks the status of each letter in the guess.
     *
     * @param answer The correct answer.
     * @param guess The user's guess.
     * @return A list of LetterStatus objects representing the status of each letter.
     */
    static List<LetterStatus> markLetters(String answer, String guess) {
        int n = Dictionary.WORD_LENGTH;
        LetterStatus[] result = new LetterStatus[n];
        boolean[] marked = new boolean[n];

        markCorrectPositions(answer, guess, result, marked);
        markRemainingPositions(answer, guess, result, marked);

        return List.of(result);
    }

    /**
     * Marks the correct positions of letters in the guess.
     *
     * @param answer The correct answer.
     * @param guess The user's guess.
     * @param result The array to store the result of marking.
     * @param marked The array to mark positions that have been processed.
     */
    private static void markCorrectPositions(String answer, String guess, LetterStatus[] result, boolean[] marked) {
        int n = Dictionary.WORD_LENGTH;
        for (int i = 0; i < n; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = LetterStatus.CORRECT;
                marked[i] = true;
            }
        }
    }

    /**
     * Marks the remaining positions of letters in the guess that are not already marked.
     *
     * @param answer The correct answer.
     * @param guess The user's guess.
     * @param result The array to store the result of marking.
     * @param marked The array to mark positions that have been processed.
     */
    private static void markRemainingPositions(String answer, String guess, LetterStatus[] result, boolean[] marked) {
        int n = Dictionary.WORD_LENGTH;
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
