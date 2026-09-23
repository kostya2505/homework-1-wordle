package academy.fiveletters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Точка входа в игру «5 букв». */
public final class Main {

    /** Логгер для логирования информации и ошибок. */
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    /** Константа для значения seed. */
    private static final long SEED = 42L;

    /** Приватный конструктор для запрета создания экземпляров этого класса. */
    private Main() {}

    /**
     * Основной метод программы, который запускает приложение.
     *
     * @param args Массив строковых аргументов, переданных при запуске программы.
     */
    static void main(String[] args) {
        LOG.debug("Аргументы запуска: {}", String.join(" ", args));

        Dictionary dictionary = DictionaryLoader.load();
        LOG.info("Словарь загружен: {} слова", dictionary.size());

        GameSession session = Game.startGame(dictionary, Game.DEFAULT_MAX_ATTEMPTS, SEED);
        LOG.info("Сессия начата: maxAttempts={}, status={}", session.getMaxAttempts(), session.getStatus());

        System.out.println("Сессия создана. Игровой цикл — в MR3.");
        System.out.println("«5 букв» — шаблон проекта Т-Академии.");
        System.out.println("Игра ещё не реализована. Начни с README.");
    }
}
