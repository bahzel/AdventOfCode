package solution.y2022;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day2_2 extends InstructionSolution<Pair<RockPaperScissors, RockPaperScissors>, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Pair<RockPaperScissors, RockPaperScissors> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		var enemy = RockPaperScissors.map(instructions[0]);
		RockPaperScissors mine = null;
		switch (instructions[1]) {
		case "X":
			switch (enemy) {
			case Rock -> mine = RockPaperScissors.Scissors;
			case Paper -> mine = RockPaperScissors.Rock;
			case Scissors -> mine = RockPaperScissors.Paper;
			}
			break;
		case "Y":
			mine = enemy;
			break;
		case "Z":
			switch (enemy) {
			case Rock -> mine = RockPaperScissors.Paper;
			case Paper -> mine = RockPaperScissors.Scissors;
			case Scissors -> mine = RockPaperScissors.Rock;
			}
		}
		return Pair.of(enemy, mine);
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
