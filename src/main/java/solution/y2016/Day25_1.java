package solution.y2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.TriConsumer;

import utils.soution.MapSolution;

public class Day25_1 extends MapSolution<List<TriConsumer<Map<String, Integer>, AtomicInteger, StringBuilder>>> {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected List<TriConsumer<Map<String, Integer>, AtomicInteger, StringBuilder>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction,
			List<TriConsumer<Map<String, Integer>, AtomicInteger, StringBuilder>> consumers) {
		var instructions = instruction.split(" ");
		switch (instructions[0]) {
		case "cpy":
			if (StringUtils.isNumeric(instructions[1])) {
				consumers.add((registers, index, output) -> registers.put(instructions[2],
						Integer.parseInt(instructions[1])));
			} else {
				consumers.add(
						(registers, index, output) -> registers.put(instructions[2], registers.get(instructions[1])));
			}
			break;
		case "inc":
			consumers.add(
					(registers, index, output) -> registers.put(instructions[1], registers.get(instructions[1]) + 1));
			break;
		case "dec":
			consumers.add(
					(registers, index, output) -> registers.put(instructions[1], registers.get(instructions[1]) - 1));
			break;
		case "jnz":
			if (StringUtils.isNumeric(instructions[1])) {
				consumers.add((registers, index, output) -> {
					if (Integer.parseInt(instructions[1]) != 0) {
						index.addAndGet(Integer.parseInt(instructions[2]) - 1);
					}
				});
			} else {
				consumers.add((registers, index, output) -> {
					if (registers.get(instructions[1]) != 0) {
						index.addAndGet(Integer.parseInt(instructions[2]) - 1);
					}
				});
			}
			break;
		case "out":
			if (StringUtils.isNumeric(instructions[1])) {
				consumers.add((registers, index, output) -> output.append(instructions[1]));
			} else {
				consumers.add((registers, index, output) -> output.append(registers.get(instructions[1])));
			}
			break;
		default:
			throw new RuntimeException("Unknown instruction: " + instruction);
		}
	}

	@Override
	protected String computeSolution(List<TriConsumer<Map<String, Integer>, AtomicInteger, StringBuilder>> consumers) {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			if (execute(i, consumers).startsWith("0101010101")) {
				return i + "";
			}
		}
		throw new IllegalStateException();
	}

	private String execute(int initialValue,
			List<TriConsumer<Map<String, Integer>, AtomicInteger, StringBuilder>> consumers) {
		var register = new HashMap<String, Integer>();
		register.put("a", initialValue);
		register.put("b", 0);
		register.put("c", 0);
		register.put("d", 0);
		var output = new StringBuilder();

		for (AtomicInteger i = new AtomicInteger(); i.get() < consumers.size(); i.incrementAndGet()) {
			consumers.get(i.get()).accept(register, i, output);
			if (output.length() > 9) {
				break;
			}
		}
		return output.toString();
	}
}
