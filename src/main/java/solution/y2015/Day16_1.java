package solution.y2015;

import org.apache.commons.lang3.tuple.Pair;
import utils.InstructionSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Day16_1 extends InstructionSolution<Pair<String, Map<String, String>>, AtomicReference<String>> {
	private final Map<String, String> HINTS = new HashMap<>() {{
		put("children", "3");
		put("cats", "7");
		put("samoyeds", "2");
		put("pomeranians", "3");
		put("akitas", "0");
		put("vizslas", "0");
		put("goldfish", "5");
		put("trees", "3");
		put("cars", "2");
		put("perfumes", "1");
	}};

	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected AtomicReference<String> initializeValue() {
		return new AtomicReference<>();
	}

	@Override
	protected Pair<String, Map<String, String>> transformInstruction(String instruction) {
		var instructions = instruction.replace(":", "").replace(",", "").split(" ");
		return Pair.of(instructions[1],
				Map.of(instructions[2], instructions[3], instructions[4], instructions[5], instructions[6],
						instructions[7]));
	}

	@Override
	protected boolean performInstruction(Pair<String, Map<String, String>> stringMapPair,
			AtomicReference<String> stringAtomicReference) {
		for (var attribute : stringMapPair.getRight().entrySet()) {
			if (!HINTS.get(attribute.getKey()).equals(attribute.getValue())) {
				return false;
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
