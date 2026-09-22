package academy.fiveletters;

import java.util.List;
import java.util.Objects;

public record Dictionary(List<String> words) {

    public static final int WORD_LENGTH = 5;

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
                throw new IllegalArgumentException("Длина слова должна быть " + WORD_LENGTH + " букв, получено " + word.length() + " букв");
            }
        }
        this.words = List.copyOf(words);
    }

    public int size() {
        return words.size();
    }

    public boolean contains(String word) {
        return word != null && words.contains(word);
    }
}
