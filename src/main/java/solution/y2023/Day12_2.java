package solution.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Day12_2 extends Day12_1 {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected Pair<String, List<Integer>> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		var springs = new ArrayList<>(Arrays.stream(instructions[1].split(",")).map(Integer::parseInt).toList());
		var size = springs.size();
		for (var i = 1; i < 5; i++) {
			for (var j = 0; j < size; j++) {
				springs.add(springs.get(j));
			}
		}

		return Pair.of(instructions[0] + "?" + instructions[0] + "?" + instructions[0] + "?" + instructions[0] + "?"
				+ instructions[0], springs);
	}
}