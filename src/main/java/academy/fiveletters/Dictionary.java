package academy.fiveletters;

import java.util.List;
import java.util.Objects;

/**
 * Represents a dictionary of words.
 *
 * <p>The dictionary is immutable and contains only words of a fixed length (5 letters).
 */
public record Dictionary(List<String> words) {

    /** The fixed length of words in the dictionary. */
    public static final int WORD_LENGTH = 5;

    /**
     * Creates a new dictionary from the given list of words.
     *
     * @param words the list of words to include in the dictionary
     * @throws NullPointerException if the list of words is null
     * @throws IllegalArgumentException if the list of words is empty, contains null values, or words of varying lengths
     */
    public Dictionary(List<String> words) {
        Objects.requireNonNull(words, "words");
        if (words.isEmpty()) {
            throw new IllegalArgumentException("Словарь не может быть пустым");
        }
        for (String word : words) {
            if (word == null) {
                throw new IllegalArgumentException("В словаре не может быть null");
            }
            if (word.length() != WORD_LENGTH) {
                throw new IllegalArgumentException(
                        "Длина слова должна быть " + WORD_LENGTH + " букв, получено " + word.length() + " букв");
            }
        }
        this.words = List.copyOf(words);
    }

    /**
     * Returns the number of words in the dictionary.
     *
     * @return the number of words in the dictionary
     */
    public int size() {
        return words.size();
    }

    /**
     * Checks if the dictionary contains the specified word.
     *
     * @param word the word to check
     * @return true if the dictionary contains the word, false otherwise
     */
    public boolean contains(String word) {
        return word != null && words.contains(word);
    }
}
