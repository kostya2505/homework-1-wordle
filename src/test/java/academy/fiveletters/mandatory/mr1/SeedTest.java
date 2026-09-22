package academy.fiveletters.mandatory.mr1;

import static org.assertj.core.api.Assertions.assertThat;

import academy.fiveletters.Dictionary;
import academy.fiveletters.DictionaryLoader;
import academy.fiveletters.Game;
import academy.fiveletters.GameSession;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: одинаковый seed обязан давать одинаковое загаданное слово. */
@DisplayName("MR1. Воспроизводимость по seed")
class SeedTest {

    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Одинаковый seed даёт одинаковое загаданное слово")
    void sameSeedProducesSameAnswer() {
        Dictionary dictionary = DictionaryLoader.load();

        GameSession firstGameSession = Game.startGame(dictionary, MAX_ATTEMPTS, 42L);
        GameSession secondGameSession = Game.startGame(dictionary, MAX_ATTEMPTS, 42L);

        assertThat(secondGameSession.getAnswer())
                .as("repeat with the same seed")
                .isEqualTo(firstGameSession.getAnswer());
    }

    @Test
    @DisplayName("Разные seed'ы дают разные слова хотя бы иногда")
    void differentSeedsProduceDifferentAnswers() {
        Dictionary dictionary = DictionaryLoader.load();

        Set<String> answers = new HashSet<>();
        for (long seed = 0; seed < 100; seed++) {
            answers.add(Game.startGame(dictionary, MAX_ATTEMPTS, seed).getAnswer());
        }

        assertThat(answers).as("more than one word on 100 different seeds").hasSizeGreaterThan(1);
    }
}
