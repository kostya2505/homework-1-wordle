package academy.fiveletters;

import java.util.List;
import java.util.Objects;

public final class GuessResult {

    private final String guess;
    private final List<LetterStatus> statuses;
    private final GameStatus gameStatus;

    GuessResult(String guess, List<LetterStatus> statuses, GameStatus gameStatus) {
        this.guess = Objects.requireNonNull(guess, "guess");
        this.statuses = List.copyOf(Objects.requireNonNull(statuses, "statuses"));
        this.gameStatus = Objects.requireNonNull(gameStatus, "gameStatus");
    }

    public String guess() {
        return guess;
    }

    public List<LetterStatus> statuses() {
        return statuses;
    }

    public LetterStatus statusAt(int index) {
        return statuses.get(index);
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public boolean isWin() {
        return gameStatus.equals(GameStatus.WIN);
    }

    public boolean isLose() {
        return gameStatus.equals(GameStatus.LOSE);
    }
}
