package utils;

import java.util.Arrays;
import java.util.List;

public class StringTransformer {
	private StringTransformer() {
	}

	public static List<String> fromString(String input) {
		return Arrays.asList(input.split(""));
	}
}