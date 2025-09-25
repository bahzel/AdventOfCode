package utils;

import java.util.Arrays;
import java.util.List;

public class StringTransformer {
    private StringTransformer() {
    }

    public static List<String> fromString(String input) {
        return Arrays.asList(input.split(""));
    }

    public static List<String> fromString(String input, int size) {
        return Arrays.asList(input.split("(?<=\\G.{" + size + "})"));
    }

    public static String removeDuplicateWhitespaces(String string) {
        return string.replaceAll("\\s+", " ");
    }
}