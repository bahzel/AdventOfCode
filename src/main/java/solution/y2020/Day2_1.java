package solution.y2020;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<Password, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Password transformInstruction(String instruction) {
		var instructions = instruction.replace("-", " ").replace(":", "").split(" ");
		return new Password(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				instructions[2].charAt(0), instructions[3]);
	}

	@Override
	protected boolean performInstruction(Password password, AtomicInteger atomicInteger) {
		var count = StringUtils.countMatches(password.getPassword(), password.getCharacter());
		if (password.getFrom() <= count && password.getTo() >= count) {
			atomicInteger.incrementAndGet();
		}
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}

@Getter
@AllArgsConstructor
class Password {
	private final int from;
	private final int to;
	private final char character;
	private final String password;
}
