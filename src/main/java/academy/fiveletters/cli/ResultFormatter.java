package academy.fiveletters.cli;

import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.LetterStatus;

public final class ResultFormatter {

    public String format(GuessResult result) {
        StringBuilder sb = new StringBuilder();
        for (LetterStatus status : result.statuses()) {
            sb.append(status.symbol());
        }
        return sb.append(' ').append(result.guess()).toString();
    }
}
