package academy.fiveletters;

import academy.fiveletters.cli.CheckCommand;
import academy.fiveletters.cli.ConsoleApp;
import academy.fiveletters.game.Dictionary;
import academy.fiveletters.game.DictionaryLoader;
import academy.fiveletters.game.Game;
import academy.fiveletters.game.GameSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Supplier;

/** Точка входа в игру «5 букв». */
public final class Main {

    /** Приватный конструктор для запрета создания экземпляров этого класса. */
    private Main() {}

    /**
     * Основной метод программы, который запускает приложение.
     *
     * @param args Массив строковых аргументов, переданных при запуске программы.
     */
    static void main(String[] args) {
        if (args.length > 0 && "check".equals(args[0])) {
            int exit = new CheckCommand(System.out).run(Arrays.copyOfRange(args, 1, args.length));
            System.exit(exit);
        }

        Dictionary dictionary = DictionaryLoader.load();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        Supplier<GameSession> gameSessionSupplier =
                () -> Game.startGame(dictionary);

        new ConsoleApp(in, System.out, gameSessionSupplier).run();
    }
}
