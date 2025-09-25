package solution.y2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import utils.soution.MapSolution;

public class Day18_1 extends MapSolution<List<Consumer<Instruction>>> {
	public static void main(String[] args) {
		new Day18_1().solve();
	}

	@Override
	protected List<Consumer<Instruction>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Consumer<Instruction>> consumers) {
		var instructions = instruction.split(" ");
		switch (instructions[0]) {
		case "snd":
			consumers.add(
					instr -> instr.setPlayedSound(instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)));
			break;
		case "set":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add(instr -> instr.getRegister().put(instructions[1], Long.parseLong(instructions[2])));
			} else {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[2], name -> 0L)));
			}
			break;
		case "add":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)
															+ Integer.parseInt(instructions[2])));
			} else {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)
															+ instr.getRegister().get(instructions[2])));
			}
			break;
		case "mul":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)
															* Integer.parseInt(instructions[2])));
			} else {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)
															* instr.getRegister().get(instructions[2])));
			}
			break;
		case "mod":
			if (NumberUtils.isParsable(instructions[2])) {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)
															% Integer.parseInt(instructions[2])));
			} else {
				consumers.add(instr -> instr.getRegister()
											.put(instructions[1],
													instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)
															% instr.getRegister().get(instructions[2])));
			}
			break;
		case "rcv":
			if (NumberUtils.isParsable(instructions[1])) {
				if (!"0".equals(instructions[1])) {
					consumers.add(instr -> instr.setRecoveredSound(instr.getPlayedSound()));
				}
			} else {
				consumers.add(instr -> {
					if (instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) != 0) {
						instr.setRecoveredSound(instr.getPlayedSound());
					}
				});
			}
			break;
		case "jgz":
			if (NumberUtils.isParsable(instructions[1])) {
				if (Integer.parseInt(instructions[1]) > 0) {
					if (NumberUtils.isParsable(instructions[2])) {
						consumers.add(instr -> instr.setCurrentIndex(
								instr.getCurrentIndex() + Integer.parseInt(instructions[2]) - 1));
					} else {
						consumers.add(instr -> instr.setCurrentIndex(
								(int) (instr.getCurrentIndex() + instr.getRegister().get(instructions[2]) - 1)));
					}
				}
			} else {
				if (NumberUtils.isParsable(instructions[2])) {
					consumers.add(instr -> {
						if (instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) > 0) {
							instr.setCurrentIndex(instr.getCurrentIndex() + Integer.parseInt(instructions[2]) - 1);
						}
					});
				} else {
					consumers.add(instr -> {
						if (instr.getRegister().computeIfAbsent(instructions[1], name -> 0L) > 0) {
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
	protected String computeSolution(List<Consumer<Instruction>> consumers) {
		var instruction = new Instruction();

		for (; instruction.getCurrentIndex() < consumers.size(); instruction.setCurrentIndex(
				instruction.getCurrentIndex() + 1)) {
			consumers.get(instruction.getCurrentIndex()).accept(instruction);

			if (instruction.getRecoveredSound() != 0) {
				return instruction.getRecoveredSound() + "";
			}
		}
		throw new IllegalStateException("No solution found");
	}
}

@Getter
@Setter
class Instruction {
	private final Map<String, Long> register;
	private long playedSound;
	private long recoveredSound;
	private int currentIndex;

	public Instruction() {
		register = new HashMap<>();
	}
}
