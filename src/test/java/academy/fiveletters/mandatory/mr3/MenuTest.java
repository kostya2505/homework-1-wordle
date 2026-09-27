package academy.fiveletters.mandatory.mr3;

import static org.assertj.core.api.Assertions.assertThat;

import academy.fiveletters.cli.CheckCommand;
import academy.fiveletters.cli.ConsoleApp;
import academy.fiveletters.game.Dictionary;
import academy.fiveletters.game.GameSession;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: меню и режим автопроверки. */
@DisplayName("MR3. Меню и детерминированный режим")
class MenuTest {

    private static final Dictionary DICT = new Dictionary(List.of("озеро", "арбуз", "сорок", "книга"));

    private static final String ANSWER = "озеро";
    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Некорректный пункт меню не роняет программу")
    void invalidMenuChoiceDoesNotCrash() {
        String output = runApp("abc\n\n2\nfoo bar\n0\n");

        assertThat(output).contains("Введите 1 или 0.");
        assertThat(output).contains("До свидания!");
    }

    @Test
    @DisplayName("Можно сыграть несколько партий подряд без перезапуска")
    void severalGamesInARow() {
        String output = runApp("1\nозеро\n1\nозеро\n0\n");

        assertThat(output.split("Победа!", -1)).hasSize(3);
    }

    @Test
    @DisplayName("Детерминированный режим даёт предсказуемый вывод для автопроверки")
    void deterministicModeProducesPredictableOutput() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);

        int exit = new CheckCommand(out, DICT).run(new String[] {
            "--answer", "озеро",
            "--guess", "арбуз",
            "--guess", "книга"
        });

        assertThat(exit).isZero();
        assertThat(bytes.toString(StandardCharsets.UTF_8))
                .isEqualTo("❌🟡❌❌🟡 арбуз\n❌❌❌❌❌ книга\n".replace("\n", System.lineSeparator()));
    }

    private static String runApp(String input) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(new StringReader(input));
        Supplier<GameSession> factory = () -> new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        new ConsoleApp(in, out, factory).run();
        return bytes.toString(StandardCharsets.UTF_8);
    }
}
