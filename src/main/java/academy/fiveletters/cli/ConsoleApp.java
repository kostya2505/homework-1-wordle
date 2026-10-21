package academy.fiveletters.cli;

import academy.fiveletters.game.GameSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.function.Supplier;

/** Represents a console application for playing the "5 letters" game. */
public final class ConsoleApp {

    private final BufferedReader in;
    private final PrintStream out;
    private final Supplier<GameSession> sessionFactory;

    /**
     * Constructs a new ConsoleApp with the specified input, output, and game session factory.
     *
     * @param in the input stream to read user commands
     * @param out the output stream to print game messages
     * @param sessionFactory the supplier to create a new game session
     */
    public ConsoleApp(BufferedReader in, PrintStream out, Supplier<GameSession> sessionFactory) {
        this.in = Objects.requireNonNull(in);
        this.out = Objects.requireNonNull(out);
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

    /** Runs the console application. */
    public void run() {
        out.println("Добро пожаловать в \"5 букв\"!");

        while (true) {
            printMenu();
            String line = readLine();
            if (line == null) {
                out.println();
                return;
            }

            switch (line.trim()) {
                case "1" -> new GameRunner(in, out, sessionFactory.get()).run();
                case "0" -> {
                    out.println("До свидания!");
                    return;
                }
                default -> out.println("Введите 1 или 0.");
            }
        }
    }

    /** Prints the main menu options. */
    private void printMenu() {
        out.println();
        out.println("1 - новая игра");
        out.println("0 - выход");
        out.print("> ");
        out.flush();
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the line of input, or null if the input stream is closed
     */
    private String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
