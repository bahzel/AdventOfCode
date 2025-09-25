package solution.y2015;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day16_2 extends InstructionSolution<Pair<String, Map<String, Integer>>, AtomicReference<String>> {
	private final Map<String, Integer> EXACT_HINTS = new HashMap<>() {
		{
			put("children", 3);
			put("samoyeds", 2);
			put("akitas", 0);
			put("vizslas", 0);
			put("cars", 2);
			put("perfumes", 1);
		}
	};
	private final Map<String, Integer> MORE_HINTS = new HashMap<>() {
		{
			put("cats", 7);
			put("trees", 3);
		}
	};
	private final Map<String, Integer> LESS_HINTS = new HashMap<>() {
		{
			put("pomeranians", 3);
			put("goldfish", 5);
		}
	};

	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected AtomicReference<String> initializeValue() {
		return new AtomicReference<>();
	}

	@Override
	protected Pair<String, Map<String, Integer>> transformInstruction(String instruction) {
		var instructions = instruction.replace(":", "").replace(",", "").split(" ");
		return Pair.of(instructions[1], Map.of(instructions[2], Integer.parseInt(instructions[3]), instructions[4],
				Integer.parseInt(instructions[5]), instructions[6], Integer.parseInt(instructions[7])));
	}

	@Override
	protected boolean performInstruction(Pair<String, Map<String, Integer>> stringMapPair,
			AtomicReference<String> stringAtomicReference) {
		for (var attribute : stringMapPair.getRight().entrySet()) {
			if (EXACT_HINTS.containsKey(attribute.getKey())) {
				if (!EXACT_HINTS.get(attribute.getKey()).equals(attribute.getValue())) {
					return false;
				}
			} else if (MORE_HINTS.containsKey(attribute.getKey())) {
				if (MORE_HINTS.get(attribute.getKey()) >= attribute.getValue()) {
					return false;
				}
			} else if (LESS_HINTS.containsKey(attribute.getKey())) {
				if (LESS_HINTS.get(attribute.getKey()) <= attribute.getValue()) {
					return false;
				}
			} else {
				throw new IllegalArgumentException("Unknown attribute: " + attribute.getKey());
			}
		}

		stringAtomicReference.set(stringMapPair.getLeft());
		return false;
	}

	@Override
	protected String getSolution(AtomicReference<String> stringAtomicReference) {
		return stringAtomicReference.get();
	}
}
