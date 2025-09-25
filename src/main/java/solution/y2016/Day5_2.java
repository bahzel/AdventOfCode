package solution.y2016;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.codec.digest.DigestUtils;

import utils.soution.Solution;

public class Day5_2 extends Solution {
	public static void main(String[] args) {
		new Day5_2().solve();
	}

	@Override
	protected String doSolve() {
		String key = input.getFirst();
		var solution = new char[8];

		for (long index = 0; index < Long.MAX_VALUE; index++) {
			var hash = DigestUtils.md5Hex(key + index);
			if (hash.startsWith("00000")) {
				if (hash.charAt(5) < '8' && solution[Integer.parseInt(hash.charAt(5) + "")] == 0) {
					solution[Integer.parseInt(hash.charAt(5) + "")] = hash.charAt(6);
					if (IntStream.range(0, solution.length).mapToObj(i -> solution[i]).noneMatch(c -> c == 0)) {
						return IntStream.range(0, solution.length)
										.mapToObj(i -> solution[i])
										.map(Object::toString)
										.collect(Collectors.joining());
					}
				}
			}
		}

		throw new IllegalStateException();
	}
}
