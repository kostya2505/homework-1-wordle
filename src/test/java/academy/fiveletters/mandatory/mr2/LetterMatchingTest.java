package academy.fiveletters.mandatory.mr2;

import static org.assertj.core.api.Assertions.assertThat;

import academy.fiveletters.Dictionary;
import academy.fiveletters.Game;
import academy.fiveletters.GameSession;
import academy.fiveletters.GuessResult;
import academy.fiveletters.LetterStatus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: раскраска букв. */
@DisplayName("MR2. Проверка букв")
class LetterMatchingTest {

    private static final Dictionary DICT = new Dictionary(List.of("озеро", "арбуз", "сорок", "оооом", "книга"));

    @Test
    @DisplayName("Базовый случай: загадано \"озеро\", ввод \"арбуз\" -> ❌🟡❌❌🟡")
    void basicCase() {
        GameSession session = new GameSession(DICT, "озеро", 6);
        GuessResult result = Game.applyGuess(session, "арбуз");

        assertThat(result.statuses())
                .containsExactly(
                        LetterStatus.ABSENT,
                        LetterStatus.PRESENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.PRESENT);
    }

    @Test
    @DisplayName("Полное совпадение: загадано \"озеро\", ввод \"озеро\" -> ✅✅✅✅✅")
    void exactMatch() {
        GameSession session = new GameSession(DICT, "озеро", 6);
        GuessResult result = Game.applyGuess(session, "озеро");

        assertThat(result.statuses())
                .containsExactly(
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT);
    }

    @Test
    @DisplayName("Повторяющиеся буквы: загадано \"сорок\", ввод \"оооом\" -> ❌✅❌✅❌")
    void repeatedLettersAreNotDoubleCounted() {
        GameSession session = new GameSession(DICT, "сорок", 6);
        GuessResult result = Game.applyGuess(session, "оооом");

        assertThat(result.statuses())
                .containsExactly(
                        LetterStatus.ABSENT,
                        LetterStatus.CORRECT,
                        LetterStatus.ABSENT,
                        LetterStatus.CORRECT,
                        LetterStatus.ABSENT);
    }

    @Test
    @DisplayName("Ни одна буква не подошла: все позиции ❌")
    void noMatchingLetters() {
        GameSession session = new GameSession(DICT, "книга", 6);
        GuessResult result = Game.applyGuess(session, "оооом");

        assertThat(result.statuses())
                .containsExactly(
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT);
    }
}
