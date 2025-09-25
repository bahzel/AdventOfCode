package solution.y2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day5_1 extends InstructionSolution<int[], Map<Point, Integer>> {
	public static void main(String[] args) {
		new Day5_1().solve();
	}

	@Override
	protected Map<Point, Integer> initializeValue() {
		return new HashMap<>();
	}

	@Override
	protected int[] transformInstruction(String instruction) {
		return Arrays.stream(instruction.replace(" -> ", ",").split(",")).mapToInt(Integer::parseInt).toArray();
	}

	@Override
	protected boolean performInstruction(int[] ints, Map<Point, Integer> setAtomicIntegerPair) {
		if (ints[0] != ints[2] && ints[1] != ints[3]) {
			return false;
		}

		var xMin = Math.min(ints[0], ints[2]);
		var xMax = Math.max(ints[0], ints[2]);
		var yMin = Math.min(ints[1], ints[3]);
		var yMax = Math.max(ints[1], ints[3]);
		for (var x = xMin; x <= xMax; x++) {
			for (var y = yMin; y <= yMax; y++) {
				var point = new Point(x, y);
				setAtomicIntegerPair.put(point, setAtomicIntegerPair.getOrDefault(point, 0) + 1);
			}
		}

		return false;
	}

	@Override
	protected String getSolution(Map<Point, Integer> setAtomicIntegerPair) {
		return setAtomicIntegerPair.values().stream().filter(pair -> pair > 1).count() + "";
	}
}
