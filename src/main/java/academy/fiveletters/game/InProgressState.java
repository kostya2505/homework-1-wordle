package academy.fiveletters.game;

public class InProgressState implements GameState {
    @Override
    public GuessResult handleGuess(GameSession session, String guess) {
        GuessResult result = Game.processGuess(session, guess);

        if (result.isWin()) {
            session.setState(new WonState());
        } else if (session.attemptsUsed() >= session.maxAttempts()) {
            session.setState(new LostState());
        }
        return result;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.IN_PROGRESS;
    }
}
