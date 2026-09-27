package academy.fiveletters.game;

/**
 * The {@code LetterStatus} enum represents the possible statuses a letter can have in a word guess game.
 */
public enum LetterStatus {
    /** Indicates that a letter is in the correct position. */
    CORRECT("✅"),
    /** Indicates that a letter is present in the word but in the wrong position. */
    PRESENT("\uD83D\uDFE1"),
    /** Indicates that a letter is not present in the word. */
    ABSENT("❌");

    private final String symbol;

    /**
     * Creates a new {@code LetterStatus} with the given symbol.
     *
     * @param symbol the symbol representing the status
     */
    LetterStatus(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this status.
     *
     * @return the symbol
     */
    public String symbol() {
        return symbol;
    }
}
