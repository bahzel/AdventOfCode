package solution.y2022;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<Pair<RockPaperScissors, RockPaperScissors>, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Pair<RockPaperScissors, RockPaperScissors> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		return Pair.of(RockPaperScissors.map(instructions[0]), RockPaperScissors.map(instructions[1]));
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}

	@Override
	protected boolean performInstruction(Pair<RockPaperScissors, RockPaperScissors> rockPaperScissorsPair,
			AtomicInteger atomicInteger) {
		atomicInteger.addAndGet(rockPaperScissorsPair.getLeft().play(rockPaperScissorsPair.getRight())
				+ rockPaperScissorsPair.getRight().getScore());
		return false;
	}
}

@AllArgsConstructor
@Getter
enum RockPaperScissors {
	Rock(1), Paper(2), Scissors(3);

	private final int score;

	public int play(RockPaperScissors other) {
		if (this == other) {
			return 3;
		} else if (this == Paper) {
			return other == RockPaperScissors.Rock ? 0 : 6;
		} else if (this == Scissors) {
			return other == RockPaperScissors.Paper ? 0 : 6;
		} else {
			return other == RockPaperScissors.Scissors ? 0 : 6;
		}
	}

	public static RockPaperScissors map(String value) {
		return switch (value) {
		case "A", "X" -> RockPaperScissors.Rock;
		case "B", "Y" -> RockPaperScissors.Paper;
		case "C", "Z" -> RockPaperScissors.Scissors;
		default -> throw new IllegalArgumentException();
		};
	}
}
