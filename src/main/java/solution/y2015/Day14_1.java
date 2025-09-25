package solution.y2015;

import lombok.AllArgsConstructor;
import utils.soution.MapSolution;

import java.util.ArrayList;
import java.util.List;

public class Day14_1 extends MapSolution<List<Reindeer>> {
	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected List<Reindeer> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Reindeer> reindeer) {
		var instructions = instruction.split(" ");
		reindeer.add(new Reindeer(Long.parseLong(instructions[3]), Long.parseLong(instructions[6]),
				Long.parseLong(instructions[13])));
	}

	@Override
	protected String computeSolution(List<Reindeer> reindeers) {
		long furthestDistance = Long.MIN_VALUE;

		for (Reindeer reindeer : reindeers) {
			furthestDistance = Math.max(furthestDistance, reindeer.computeDistance(2503));
		}

		return furthestDistance + "";
	}
}

@AllArgsConstructor
class Reindeer {
	private final long speed;
	private final long flyTime;
	private final long restTime;

	public long computeDistance(long duration) {
		long fullCycles = duration / (flyTime + restTime);
		long timeLeft = duration % (flyTime + restTime);

		if (timeLeft > flyTime) {
			return (fullCycles + 1) * flyTime * speed;
		} else {
			return fullCycles * flyTime * speed + timeLeft * speed;
		}
	}
}
