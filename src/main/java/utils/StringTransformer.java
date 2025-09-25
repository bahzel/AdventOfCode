package utils;

import java.util.Arrays;
import java.util.List;

public class StringTransformer {
    private StringTransformer() {
    }

    /**
     * Returns a list of all characters of the given String
     *
     * @param input input
     * @return A list of all characters of the given String
     */
    public static List<String> fromString(String input) {
        return Arrays.asList(input.split(""));
    }

    /**
     * Returns a list of substrings with the given length of the given String
     *
     * @param input input
     * @param size  size of the substrings
     * @return A list of substrings with the given length of the given String
     */
    public static List<String> fromString(String input, int size) {
        return Arrays.asList(input.split("(?<=\\G.{" + size + "})"));
    }

    public static String removeDuplicateWhitespaces(String string) {
        return string.replaceAll("\\s+", " ");
    }
}