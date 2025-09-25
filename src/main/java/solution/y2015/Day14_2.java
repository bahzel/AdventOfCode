package solution.y2015;

import lombok.Getter;
import utils.MapSolution;

import java.util.ArrayList;
import java.util.List;

public class Day14_2 extends MapSolution<List<ReindeerWithPoints>> {
	public static void main(String[] args) {
		new Day14_2().solve();
	}

	@Override
	protected List<ReindeerWithPoints> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<ReindeerWithPoints> reindeer) {
		var instructions = instruction.split(" ");
		reindeer.add(new ReindeerWithPoints(Long.parseLong(instructions[3]), Long.parseLong(instructions[6]),
				Long.parseLong(instructions[13])));
	}

	@Override
	protected String computeSolution(List<ReindeerWithPoints> reindeers) {
		for (int i = 1; i <= 2503; i++) {
			long furthestDistance = Long.MIN_VALUE;

			for (ReindeerWithPoints reindeer : reindeers) {
				furthestDistance = Math.max(furthestDistance, reindeer.computeDistance(i));
			}

			final long winnerDistance = furthestDistance;
			reindeers.stream()
					 .filter(reindeer -> reindeer.getDistance() == winnerDistance)
					 .forEach(ReindeerWithPoints::grantPoint);
		}

		return reindeers.stream().map(ReindeerWithPoints::getPoints).mapToLong(Long::longValue).max().orElseThrow()
				+ "";
	}
}

class ReindeerWithPoints {
	private final long speed;
	private final long flyTime;
	private final long restTime;
	@Getter
	private long points;
	@Getter
	private long distance;

	public ReindeerWithPoints(long speed, long flyTime, long restTime) {
		this.speed = speed;
		this.flyTime = flyTime;
		this.restTime = restTime;
	}

	public long computeDistance(long duration) {
		long fullCycles = duration / (flyTime + restTime);
		long timeLeft = duration % (flyTime + restTime);

		if (timeLeft > flyTime) {
			distance = (fullCycles + 1) * flyTime * speed;
		} else {
			distance = fullCycles * flyTime * speed + timeLeft * speed;
		}

		return distance;
	}

	public void grantPoint() {
		points++;
	}
}
