package academy.fiveletters.cli;

import academy.fiveletters.game.GameSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.function.Supplier;

public final class ConsoleApp {

    private final BufferedReader in;
    private final PrintStream out;
    private final Supplier<GameSession> sessionFactory;

    public ConsoleApp(BufferedReader in, PrintStream out, final Supplier<GameSession> sessionFactory) {
        this.in = Objects.requireNonNull(in);
        this.out = Objects.requireNonNull(out);
        this.sessionFactory = Objects.requireNonNull(sessionFactory);
    }

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

    private void printMenu() {
        out.println();
        out.println("1 - новая игра");
        out.println("0 - выход");
        out.print("> ");
        out.flush();
    }

    private String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
