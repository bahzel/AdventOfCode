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
}