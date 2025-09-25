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

    public static String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("a", "1010");
        hex = hex.replaceAll("b", "1011");
        hex = hex.replaceAll("c", "1100");
        hex = hex.replaceAll("d", "1101");
        hex = hex.replaceAll("e", "1110");
        hex = hex.replaceAll("f", "1111");
        return hex;
    }
}