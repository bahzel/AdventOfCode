package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13_1 extends MapSolution<Map<Pair<String, String>, Integer>> {
	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected Map<Pair<String, String>, Integer> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<Pair<String, String>, Integer> pairIntegerMap) {
		var instructions = instruction.replace(".", "").split(" ");

		int happiness;
		if ("gain".equals(instructions[2])) {
			happiness = Integer.parseInt(instructions[3]);
		} else if ("lose".equals(instructions[2])) {
			happiness = -Integer.parseInt(instructions[3]);
		} else {
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}

		pairIntegerMap.put(Pair.of(instructions[0], instructions[10]), happiness);
	}

	@Override
	protected String computeSolution(Map<Pair<String, String>, Integer> pairIntegerMap) {
		var toSeat = new ArrayList<>(pairIntegerMap.keySet().stream().map(Pair::getLeft).distinct().toList());
		var seated = new ArrayList<String>();
		seated.add(toSeat.getFirst());
		toSeat.removeFirst();
		return checkHappiness(seated, toSeat, pairIntegerMap) + "";
	}

	private long checkHappiness(List<String> seated, List<String> toSeat,
			Map<Pair<String, String>, Integer> pairIntegerMap) {
		if (toSeat.isEmpty()) {
			return computeHappiness(seated, pairIntegerMap);
		}

		long happiness = 0;
		for (var neighbour : toSeat) {
			List<String> newSeated = new ArrayList<>(seated);
			newSeated.add(neighbour);
			List<String> newToSeat = new ArrayList<>(toSeat);
			newToSeat.remove(neighbour);
			happiness = Math.max(happiness, checkHappiness(newSeated, newToSeat, pairIntegerMap));
		}

		return happiness;
	}

	private long computeHappiness(List<String> seated, Map<Pair<String, String>, Integer> pairIntegerMap) {
		long happiness = 0;

		for (int i = 0; i < seated.size() - 1; i++) {
			happiness += pairIntegerMap.get(Pair.of(seated.get(i), seated.get(i + 1)));
		}
		for (int i = seated.size() - 1; i > 0; i--) {
			happiness += pairIntegerMap.get(Pair.of(seated.get(i), seated.get(i - 1)));
		}
		happiness += pairIntegerMap.get(Pair.of(seated.getLast(), seated.getFirst()));
		happiness += pairIntegerMap.get(Pair.of(seated.getFirst(), seated.getLast()));

		return happiness;
	}
}
