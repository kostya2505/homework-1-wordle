package academy.fiveletters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Точка входа в игру «5 букв». */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Main() {}

    static void main(String[] args) {
        LOG.debug("Аргументы запуска: {}", String.join(" ", args));

        Dictionary dictionary = DictionaryLoader.load();
        LOG.info("Словарь загружен: {} слова", dictionary.size());

        GameSession session = Game.startGame(dictionary, Game.DEFAULT_MAX_ATTEMPTS, 42L);
        LOG.info("Сессия начата: maxAttempts={}, status={}", session.getMaxAttempts(), session.getStatus());

        System.out.println("«5 букв» — шаблон проекта Т-Академии.");
        System.out.println("Игра ещё не реализована. Начни с README.");
    }
}
