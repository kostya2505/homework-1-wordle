package academy.fiveletters.cli;

import academy.fiveletters.game.Game;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GameStatus;
import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.InvalidGuessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Objects;

public final class GameRunner {

    private static final int DECIMAL_BASE = 10;
    private static final int HUNDRED_BASE = 100;

    private static final int TEENS_FROM = 11;
    private static final int TEENS_TO = 14;
    private static final int FEW_FROM = 2;
    private static final int FEW_TO = 4;
    private static final int ONE = 1;

    private final BufferedReader in;
    private final PrintStream out;
    private final GameSession gameSession;
    private final ResultFormatter formatter = new ResultFormatter();

    public GameRunner(BufferedReader in, PrintStream out, GameSession gameSession) {
        this.in = Objects.requireNonNull(in);
        this.out = Objects.requireNonNull(out);
        this.gameSession = Objects.requireNonNull(gameSession);
    }

    public void run() {
        while (gameSession.status() == GameStatus.IN_PROGRESS) {
            out.print("> ");
            out.flush();

            String line = readLine();
            if (line == null) {
                return;
            }

            try {
                GuessResult result = Game.applyGuess(gameSession, line);

                out.println(formatter.format(result));

                if (result.isWin()) {
                    out.println("Победа! Слово угадано за "
                            + gameSession.attemptsUsed() + " "
                            + attemptsWord(gameSession.attemptsUsed()));
                } else if (result.isLose()) {
                    out.println("Неудача. Загаданное слово: " + gameSession.answer());
                } else {
                    out.println("Осталось попыток: " + (gameSession.maxAttempts() - gameSession.attemptsUsed()));
                }
            } catch (InvalidGuessException e) {
                out.println(e.getMessage());
            }
        }
    }

    static String attemptsWord(int attempts) {
        int mod10 = attempts % DECIMAL_BASE;
        int mod100 = attempts % HUNDRED_BASE;
        if (mod100 >= TEENS_FROM && mod100 <= TEENS_TO) {
            return "попыток";
        }
        if (mod10 == ONE) {
            return "попытку";
        }
        if (mod10 >= FEW_FROM && mod10 <= FEW_TO) {
            return "попытки";
        }
        return "попыток";
    }

    private String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
