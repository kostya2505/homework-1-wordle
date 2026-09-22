package academy.fiveletters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** The {@code DictionaryLoader} class provides methods for loading and parsing a dictionary file. */
public final class DictionaryLoader {

    /** The default resource path for the dictionary file. */
    public static final String DEFAULT_RESOURCE = "/dictionary.txt";

    /** Private constructor to prevent instantiation of the utility class. */
    private DictionaryLoader() {}

    /**
     * Loads the default dictionary resource.
     *
     * @return the loaded dictionary
     */
    public static Dictionary load() {
        return load(DEFAULT_RESOURCE);
    }

    /**
     * Loads a dictionary from the specified resource.
     *
     * @param resource the path to the resource file
     * @return the loaded dictionary
     * @throws IllegalArgumentException if the resource is null
     * @throws UncheckedIOException if an I/O error occurs
     */
    public static Dictionary load(String resource) {
        Objects.requireNonNull(resource, "resource");
        try (InputStream in = DictionaryLoader.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalArgumentException("Ресурс " + resource + " не найден");
            }
            return parse(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось прочитать ресурс: " + resource, e);
        }
    }

    /**
     * Parses the input stream to create a dictionary.
     *
     * @param in the input stream containing the dictionary data
     * @return the parsed dictionary
     * @throws IOException if an I/O error occurs
     */
    static Dictionary parse(InputStream in) throws IOException {
        Objects.requireNonNull(in, "in");
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }
                words.add(trimmedLine);
            }
        }
        return new Dictionary(words);
    }
}
