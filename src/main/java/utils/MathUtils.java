package utils;

public class MathUtils {
	private MathUtils() {
	}

	public static int greatestCommonDivisor(int a, int b) {
		if (b == 0) {
			return a;
		}
		return greatestCommonDivisor(b, a % b);
	}

	public static long greatestCommonDivisor(long a, long b) {
		if (b == 0) {
			return a;
		}
		return greatestCommonDivisor(b, a % b);
	}

	public static long leastCommonMultiple(long a, long b) {
		return a * (b / greatestCommonDivisor(a, b));
	}
}