package academy.fiveletters.cli;

import academy.fiveletters.game.Dictionary;
import academy.fiveletters.game.DictionaryLoader;
import academy.fiveletters.game.Game;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.InvalidGuessException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a command to check word guesses against an answer.
 */
public final class CheckCommand {

    private final PrintStream out;
    private final Dictionary dictionary;

    /**
     * Constructs a new CheckCommand with the specified output stream and dictionary.
     *
     * @param out       the output stream to use for printing messages
     * @param dictionary the dictionary to use for the game
     */
    public CheckCommand(PrintStream out, Dictionary dictionary) {
        this.out = Objects.requireNonNull(out);
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    /**
     * Constructs a new CheckCommand with the specified output stream and a default dictionary.
     *
     * @param out the output stream to use for printing messages
     */
    public CheckCommand(PrintStream out) {
        this(out, DictionaryLoader.load());
    }

    /**
     * Runs the command with the provided arguments.
     *
     * @param args the command line arguments
     * @return the exit code (0 for success, non-zero for failure)
     */
    public int run(String[] args) {
        String answer = null;
        List<String> guesses = new ArrayList<>();

        try {
            int i = 0;
            while (i < args.length) {
                String flag = args[i];
                switch (flag) {
                    case "--answer" -> {
                        answer = requireValue(args, ++i, "--answer");
                        i++;
                    }
                    case "--guess" -> {
                        guesses.add(requireValue(args, ++i, "--guess"));
                        i++;
                    }
                    default -> {
                        out.println("Неизвестный аргумент: " + flag);
                        return 1;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
            return 1;
        }

        if (answer == null) {
            out.println("Не указан --answer");
            return 1;
        }
        if (guesses.isEmpty()) {
            out.println("Не указаны --guess");
            return 1;
        }

        GameSession gameSession;

        try {
            int maxAttempts = Math.max(guesses.size(), Game.DEFAULT_MAX_ATTEMPTS);
            gameSession = new GameSession(dictionary, answer, maxAttempts);
        } catch (IllegalArgumentException e) {
            out.println("Некорректный ответ: " + e.getMessage());
            return 1;
        }

        ResultFormatter formatter = new ResultFormatter();
        for (String guess : guesses) {
            try {
                GuessResult result = Game.applyGuess(gameSession, guess);
                out.println(formatter.format(result));
            } catch (InvalidGuessException e) {
                out.println(e.getMessage());
                return 1;
            }
        }
        return 0;
    }

    /**
     * Requires a value for a command line flag.
     *
     * @param args the command line arguments
     * @param i    the index of the flag value
     * @param s    the flag name
     * @return the flag value
     * @throws IllegalArgumentException if the flag value is missing
     */
    private String requireValue(String[] args, int i, String s) {
        if (i >= args.length) {
            throw new IllegalArgumentException("Флаг " + s + " требует значения");
        }
        return args[i];
    }
}
