package academy.fiveletters.mandatory.mr1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import academy.fiveletters.game.Dictionary;
import academy.fiveletters.game.DictionaryLoader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: словарь. */
@DisplayName("MR1. Словарь")
class DictionaryTest {

    @Test
    @DisplayName("Словарь содержит не меньше 50 слов")
    void dictionaryContainsAtLeastFiftyWords() {
        Dictionary dictionary = DictionaryLoader.load();

        assertThat(dictionary.size()).as("словарь из dictionary.txt").isGreaterThanOrEqualTo(50);
    }

    @Test
    @DisplayName("Все слова словаря состоят ровно из 5 букв")
    void allWordsAreExactlyFiveLettersLong() {
        Dictionary dictionary = DictionaryLoader.load();

        assertThat(dictionary.words())
                .isNotEmpty()
                .allSatisfy(
                        word -> assertThat(word).as("слово из dictionary.txt").hasSize(Dictionary.WORD_LENGTH));
    }

    @Test
    @DisplayName("Пустой словарь приводит к ошибке, а не к запуску игры без слова")
    void emptyDictionaryIsRejected() {
        assertThatThrownBy(() -> new Dictionary(List.of()))
                .as("пустой список слов не может быть принят конструктором")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
