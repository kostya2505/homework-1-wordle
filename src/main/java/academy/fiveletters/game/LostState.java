package academy.fiveletters.game;

public class LostState implements GameState {
    @Override
    public GuessResult handleGuess(GameSession session, String guess) {
        throw new IllegalStateException("Игра уже проиграна");
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.LOST;
    }
}
