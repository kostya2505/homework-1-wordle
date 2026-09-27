package academy.fiveletters.cli;

import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.LetterStatus;

/**
 * The ResultFormatter class formats the result of a guess for display in the console.
 */
public final class ResultFormatter {

    /**
     * Formats the GuessResult into a string for display.
     *
     * @param result the GuessResult to format
     * @return a string representing the formatted result
     */
    public String format(GuessResult result) {
        StringBuilder sb = new StringBuilder();
        for (LetterStatus status : result.statuses()) {
            sb.append(status.symbol());
        }
        return sb.append(' ').append(result.guess()).toString();
    }
}
