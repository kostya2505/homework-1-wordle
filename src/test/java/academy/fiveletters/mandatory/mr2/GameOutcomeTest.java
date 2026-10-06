package academy.fiveletters.mandatory.mr2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import academy.fiveletters.game.Dictionary;
import academy.fiveletters.game.Game;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GameStatus;
import academy.fiveletters.game.GuessResult;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: завершение партии. */
@DisplayName("MR2. Победа и поражение")
class GameOutcomeTest {

    private static final Dictionary DICT = new Dictionary(List.of("озеро", "арбуз", "сорок", "книга"));

    private static final String ANSWER = "озеро";
    private static final String GUESS = "книга";
    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Угаданное слово переводит сессию в статус WIN")
    void correctGuessWinsTheGame() {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        Game.applyGuess(gameSession, GUESS);
        GuessResult last = Game.applyGuess(gameSession, ANSWER);

        assertThat(last.isWin()).isTrue();
        assertThat(gameSession.status()).isEqualTo(GameStatus.WON);
        assertThat(gameSession.attemptsUsed()).isEqualTo(2);
        assertThatThrownBy(() -> Game.applyGuess(gameSession, GUESS)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("После 6 неудачных попыток сессия переходит в статус LOSE")
    void sixFailedAttemptsLoseTheGame() {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            Game.applyGuess(gameSession, GUESS);
        }

        assertThat(gameSession.status()).isEqualTo(GameStatus.LOST);
        assertThat(gameSession.attemptsUsed()).isEqualTo(6);
        assertThat(gameSession.answer()).isEqualTo(ANSWER);
        assertThatThrownBy(() -> Game.applyGuess(gameSession, ANSWER)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("При поражении показывается загаданное слово")
    void answerIsRevealedOnLoss() {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            Game.applyGuess(gameSession, GUESS);
        }

        assertThat(gameSession.status()).isEqualTo(GameStatus.LOST);
        assertThat(gameSession.answer())
                .as("модель раскрывает ответ после поражения")
                .isEqualTo(ANSWER);
    }

    @Test
    @DisplayName("Завершённая партия больше не принимает попытки")
    void finishedGameRejectsFurtherGuesses() {
        GameSession gameSession = new GameSession(DICT, ANSWER, MAX_ATTEMPTS);

        Game.applyGuess(gameSession, ANSWER);
        assertThat(gameSession.status()).isEqualTo(GameStatus.WON);

        int attempts = gameSession.attemptsUsed();

        assertThatThrownBy(() -> Game.applyGuess(gameSession, GUESS))
                .as("партия завершена")
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> Game.applyGuess(gameSession, ANSWER))
                .as("партия завершена")
                .isInstanceOf(IllegalStateException.class);

        assertThat(gameSession.attemptsUsed())
                .as("попытки после завершения партии не учитываются")
                .isEqualTo(attempts);
    }
}
