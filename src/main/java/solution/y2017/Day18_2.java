package solution.y2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import utils.soution.MapSolution;

public class Day18_2 extends MapSolution<List<Consumer<BufferedInstruction>>> {
	public static void main(String[] args) {
		new Day18_2().solve();
	}

	@Override
	protected List<Consumer<BufferedInstruction>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Consumer<BufferedInstruction>> consumers) {
		var instructions = instruction.split(" ");
		switch (instructions[0]) {
		case "snd":
			if (NumberUtils.isParsable(instructions[1])) {
				consumers.add(instr -> instr.getSendBuffer().add(Long.parseLong(instructions[1])));
			} else {
				consumers.add(instr -> instr.getSendBuffer()
											.add(instr.getRegister().computeIfAbsent(instructions[1], name -> 0L)));
			}
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
			consumers.add(instr -> {
				if (instr.getReceiveBuffer().isEmpty()) {
					instr.setStop(true);
				} else {
					instr.getRegister().put(instructions[1], instr.getReceiveBuffer().poll());
				}
			});
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
	protected String computeSolution(List<Consumer<BufferedInstruction>> consumers) {
		var sendBuffer0 = new LinkedList<Long>();
		var sendBuffer1 = new LinkedList<Long>();
		var program0 = new BufferedInstruction(sendBuffer0, sendBuffer1);
		var program1 = new BufferedInstruction(sendBuffer1, sendBuffer0);
		program0.getRegister().put("p", 0L);
		program1.getRegister().put("p", 1L);

		var sendCount = 0;
		while (true) {
			executeProgram(program0, consumers);
			if (program0.getSendBuffer().isEmpty()) {
				return sendCount + "";
			}

			executeProgram(program1, consumers);
			if (program1.getSendBuffer().isEmpty()) {
				return sendCount + "";
			}
			sendCount += program1.getSendBuffer().size();
		}
	}

	private void executeProgram(BufferedInstruction program, List<Consumer<BufferedInstruction>> consumers) {
		for (; true; program.setCurrentIndex(program.getCurrentIndex() + 1)) {
			consumers.get(program.getCurrentIndex()).accept(program);

			if (program.isStop()) {
				program.setStop(false);
				return;
			}
		}
	}
}

@Getter
@Setter
class BufferedInstruction {
	private final Map<String, Long> register = new HashMap<>();
	private final Queue<Long> sendBuffer;
	private final Queue<Long> receiveBuffer;
	private int currentIndex;
	private boolean stop = false;

	BufferedInstruction(Queue<Long> sendBuffer, Queue<Long> receiveBuffer) {
		this.sendBuffer = sendBuffer;
		this.receiveBuffer = receiveBuffer;
	}
}
