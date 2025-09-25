package solution.y2017;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.math.NumberUtils;
import utils.soution.MapSolution;

public class Day23_1 extends MapSolution<List<BiConsumer<Instruction, AtomicLong>>> {
	public static void main(String[] args) {
		new Day23_1().solve();
	}

	@Override
	protected List<BiConsumer<Instruction, AtomicLong>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<BiConsumer<Instruction, AtomicLong>> consumers) {
		var instructions = instruction.split(" ");
		switch (instructions[0]) {
		case "set":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add(
						(instr, counter) -> instr.getRegister().put(instructions[1], Long.parseLong(instructions[2])));
			} else {
				consumers.add((instr, counter) -> instr.getRegister()
													   .put(instructions[1], instr.getRegister()
																				  .computeIfAbsent(instructions[2],
																						  name -> 0L)));
			}
			break;
		case "sub":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add((instr, counter) -> instr.getRegister()
													   .put(instructions[1], instr.getRegister()
																				  .computeIfAbsent(instructions[1],
																						  name -> 0L)
															   - Integer.parseInt(instructions[2])));
			} else {
				consumers.add((instr, counter) -> instr.getRegister()
													   .put(instructions[1], instr.getRegister()
																				  .computeIfAbsent(instructions[1],
																						  name -> 0L)
															   - instr.getRegister().get(instructions[2])));
			}
			break;
		case "mul":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add((instr, counter) -> {
					instr.getRegister()
						 .put(instructions[1],
								 instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) * Integer.parseInt(
										 instructions[2]));
					counter.incrementAndGet();
				});
			} else {
				consumers.add((instr, counter) -> {
					instr.getRegister()
						 .put(instructions[1],
								 instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) * instr.getRegister()
																										 .get(instructions[2]));
					counter.incrementAndGet();
				});
			}
			break;
		case "jnz":
			if (NumberUtils.isParsable(instructions[1])) {
				if (Integer.parseInt(instructions[1]) != 0) {
					if (NumberUtils.isParsable(instructions[2])) {
						consumers.add((instr, counter) -> instr.setCurrentIndex(
								instr.getCurrentIndex() + Integer.parseInt(instructions[2]) - 1));
					} else {
						consumers.add((instr, counter) -> instr.setCurrentIndex(
								(int) (instr.getCurrentIndex() + instr.getRegister().get(instructions[2]) - 1)));
					}
				}
			} else {
				if (NumberUtils.isParsable(instructions[2])) {
					consumers.add((instr, counter) -> {
						if (instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) != 0) {
							instr.setCurrentIndex(instr.getCurrentIndex() + Integer.parseInt(instructions[2]) - 1);
						}
					});
				} else {
					consumers.add((instr, counter) -> {
						if (instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) != 0) {
							instr.setCurrentIndex(
									(int) (instr.getCurrentIndex() + instr.getRegister().get(instructions[2]) - 1));
						}
					});
				}
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid instruction: " + instruction);
		}
	}

	@Override
	protected String computeSolution(List<BiConsumer<Instruction, AtomicLong>> consumers) {
		var instruction = new Instruction();
		var counter = new AtomicLong();

		for (; instruction.getCurrentIndex() < consumers.size(); instruction.setCurrentIndex(
				instruction.getCurrentIndex() + 1)) {
			consumers.get(instruction.getCurrentIndex()).accept(instruction, counter);
		}
		return counter.toString();
	}
}
