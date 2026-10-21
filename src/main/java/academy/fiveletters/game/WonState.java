package academy.fiveletters.game;

public class WonState implements GameState {
    @Override
    public GuessResult handleGuess(GameSession session, String guess) {
        throw new IllegalStateException("Игра уже выиграна");
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.WON;
    }
}
