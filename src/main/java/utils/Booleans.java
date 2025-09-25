package utils;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Booleans {
	private Booleans() {
	}

	public static Stream<Boolean> stream(boolean[] array) {
		return IntStream.range(0, array.length).mapToObj(idx -> array[idx]);
	}
}