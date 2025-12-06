package utils;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class StreamUtils {
	private StreamUtils() {
	}

	public static long product(LongStream stream) {
		return stream.reduce(1, (x, y) -> x * y);
	}

	public static long product(IntStream stream) {
		return stream.reduce(1, (x, y) -> x * y);
	}
}