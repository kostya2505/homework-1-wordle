package academy.fiveletters.mandatory.mr2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import academy.fiveletters.game.Dictionary;
import academy.fiveletters.game.Game;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GameStatus;
import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.InvalidGuessException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Обязательные тесты: валидация ввода. */
@DisplayName("MR2. Валидация ввода")
class GuessValidationTest {

    private static final Dictionary DICT = new Dictionary(List.of("озеро", "арбуз", "книга", "сорок", "банан"));

    private static final String ANSWER = "озеро";
    private static final int MAX_ATTEMPTS = 6;

    @ParameterizedTest
    @ValueSource(strings = {"дом", "домики", ""})
    @DisplayName("Слово не из 5 букв отклоняется: \"{0}\"")
    void wordOfWrongLengthIsRejected(String guess) {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        assertThatThrownBy(() -> Game.applyGuess(gameSession, guess)).isInstanceOf(InvalidGuessException.class);
        assertThat(gameSession.attemptsUsed()).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {"дом12", "дом!!", "до ма"})
    @DisplayName("Ввод с не-буквами отклоняется: \"{0}\"")
    void nonLetterInputIsRejected(String guess) {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        assertThatThrownBy(() -> Game.applyGuess(gameSession, guess)).isInstanceOf(InvalidGuessException.class);
        assertThat(gameSession.attemptsUsed()).isZero();
    }

    @Test
    @DisplayName("Слово, которого нет в словаре, отклоняется")
    void wordOutsideDictionaryIsRejected() {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        assertThatThrownBy(() -> Game.applyGuess(gameSession, "слово")).isInstanceOf(InvalidGuessException.class);
        assertThat(gameSession.attemptsUsed()).isZero();
    }

    @Test
    @DisplayName("Некорректный ввод не тратит попытку")
    void invalidInputDoesNotConsumeAttempt() {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        assertThatThrownBy(() -> Game.applyGuess(gameSession, "дом")).isInstanceOf(InvalidGuessException.class);
        assertThatThrownBy(() -> Game.applyGuess(gameSession, "дом!!")).isInstanceOf(InvalidGuessException.class);
        assertThatThrownBy(() -> Game.applyGuess(gameSession, "слово")).isInstanceOf(InvalidGuessException.class);

        assertThat(gameSession.attemptsUsed())
                .as("ни один некорректный ввод не тратит попытку")
                .isZero();
        assertThat(gameSession.status()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("Ввод не зависит от регистра: \"ОЗЕРО\" и \"озеро\" обрабатываются одинаково")
    void inputIsCaseInsensitive() {
        GameSession lower = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);
        GameSession upper = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        GuessResult lowerResult = Game.applyGuess(lower, "озеро");
        GuessResult upperResult = Game.applyGuess(upper, "ОЗЕРО");

        assertThat(lowerResult.statuses()).as("ввод не зависит от регистра").isEqualTo(upperResult.statuses());
        assertThat(upperResult.guess()).as("ввод переводится в нижний регистр").isEqualTo("озеро");
        assertThat(upper.status())
                .as("регистр не влияет")
                .isEqualTo(lower.status())
                .isEqualTo(GameStatus.WON);
    }
}
