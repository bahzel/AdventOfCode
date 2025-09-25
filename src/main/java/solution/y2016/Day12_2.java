package solution.y2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.StringUtils;

import utils.soution.MapSolution;

public class Day12_2 extends MapSolution<List<BiConsumer<Map<String, Integer>, AtomicInteger>>> {
	public static void main(String[] args) {
		new Day12_2().solve();
	}

	@Override
	protected List<BiConsumer<Map<String, Integer>, AtomicInteger>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction,
			List<BiConsumer<Map<String, Integer>, AtomicInteger>> consumers) {
		var instructions = instruction.split(" ");
		switch (instructions[0]) {
		case "cpy":
			if (StringUtils.isNumeric(instructions[1])) {
				consumers.add((registers, index) -> registers.put(instructions[2], Integer.parseInt(instructions[1])));
			} else {
				consumers.add((registers, index) -> registers.put(instructions[2], registers.get(instructions[1])));
			}
			break;
		case "inc":
			consumers.add((registers, index) -> registers.put(instructions[1], registers.get(instructions[1]) + 1));
			break;
		case "dec":
			consumers.add((registers, index) -> registers.put(instructions[1], registers.get(instructions[1]) - 1));
			break;
		case "jnz":
			if (StringUtils.isNumeric(instructions[1])) {
				consumers.add((registers, index) -> {
					if (Integer.parseInt(instructions[1]) != 0) {
						index.addAndGet(Integer.parseInt(instructions[2]) - 1);
					}
				});
			} else {
				consumers.add((registers, index) -> {
					if (registers.get(instructions[1]) != 0) {
						index.addAndGet(Integer.parseInt(instructions[2]) - 1);
					}
				});
			}
		}
	}

	@Override
	protected String computeSolution(List<BiConsumer<Map<String, Integer>, AtomicInteger>> consumers) {
		var register = new HashMap<String, Integer>();
		register.put("a", 0);
		register.put("b", 0);
		register.put("c", 1);
		register.put("d", 0);

		for (AtomicInteger i = new AtomicInteger(); i.get() < consumers.size(); i.incrementAndGet()) {
			consumers.get(i.get()).accept(register, i);
		}

		return register.get("a") + "";
	}
}
