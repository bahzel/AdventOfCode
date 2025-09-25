package solution.y2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day5_2 extends InstructionSolution<int[], Map<Point, Integer>> {
	public static void main(String[] args) {
		new Day5_2().solve();
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
			var x = ints[0];
			var y = ints[1];
			var point = new Point(x, y);
			setAtomicIntegerPair.put(point, setAtomicIntegerPair.getOrDefault(point, 0) + 1);
			while (x != ints[2]) {
				if (x < ints[2]) {
					x++;
				} else {
					x--;
				}
				if (y < ints[3]) {
					y++;
				} else {
					y--;
				}
				point = new Point(x, y);
				setAtomicIntegerPair.put(point, setAtomicIntegerPair.getOrDefault(point, 0) + 1);
			}
		} else {
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
		}

		return false;
	}

	@Override
	protected String getSolution(Map<Point, Integer> setAtomicIntegerPair) {
		return setAtomicIntegerPair.values().stream().filter(pair -> pair > 1).count() + "";
	}
}
