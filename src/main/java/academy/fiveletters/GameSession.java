package academy.fiveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameSession {

    private final String answer;
    private final int maxAttempts;
    private final List<String> attemptsHistory = new ArrayList<>();
    private GameStatus status = GameStatus.IN_PROGRESS;

    public GameSession(String answer, int maxAttempts) {
        Objects.requireNonNull(answer, "answer");
        if (answer.length() != Dictionary.WORD_LENGTH) {
            throw new IllegalArgumentException("Слова должны иметь только " + Dictionary.WORD_LENGTH + " букв, получено: " + answer.length() + " букв");
        }
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Максимальное количество попыток должно быть больше 0: " + maxAttempts);
        }
        this.answer = answer;
        this.maxAttempts = maxAttempts;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsUsed() {
        return attemptsHistory.size();
    }

    public List<String> getAttemptsHistory() {
        return Collections.unmodifiableList(attemptsHistory);
    }

    public GameStatus getStatus() {
        return status;
    }

    void addAttempt(String attempt) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Игра уже завершена со статусом: " + status);
        }
        attemptsHistory.add(attempt);
    }

    void setStatus(GameStatus newStatus) {
        this.status = Objects.requireNonNull(newStatus, "newStatus");
    }
}
