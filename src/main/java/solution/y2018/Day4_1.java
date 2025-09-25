package solution.y2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import utils.soution.Solution;

public class Day4_1 extends Solution {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected void initialize() {
		Collections.sort(input);
	}

	@Override
	protected String doSolve() {
		var guardList = getGuardList();
		return guardList.stream().max(Comparator.comparingInt(Guard::getSleepingMinutes)).orElseThrow().getScore() + "";
	}

	private List<Guard> getGuardList() {
		var guards = new HashMap<Long, Guard>();
		Guard currentGuard = null;
		var startTime = 0;

		for (var instruction : input) {
			var instructions = instruction.replace("] ", ":").split(":");
			var time = Integer.parseInt(instructions[1]);
			if (instructions[2].startsWith("Guard")) {
				var id = Long.parseLong(instructions[2].split(" ")[1].substring(1));
				currentGuard = guards.computeIfAbsent(id, Guard::new);
			} else if (instructions[2].startsWith("falls")) {
				startTime = time;
			} else if (instructions[2].startsWith("wakes")) {
				currentGuard.addAsleepTime(Pair.of(startTime, time));
			}
		}

		return new ArrayList<>(guards.values());
	}
}

@Getter
class Guard {
	private final long id;
	private final List<Pair<Integer, Integer>> asleepTimes;

	public Guard(long id) {
		this.id = id;
		this.asleepTimes = new ArrayList<>();
	}

	public void addAsleepTime(Pair<Integer, Integer> time) {
		this.asleepTimes.add(time);
	}

	public int getSleepingMinutes() {
		return asleepTimes.stream().mapToInt(pair -> pair.getRight() - pair.getLeft()).sum();
	}

	public Pair<Integer, Long> getMaximumSleepingMinute() {
		var maximumCount = 0L;
		var maximumMinute = Integer.MIN_VALUE;

		for (int i = 0; i <= 60; i++) {
			var currentMinute = i;
			var sleepCount = asleepTimes.stream()
										.filter(pair -> currentMinute >= pair.getLeft()
												&& currentMinute < pair.getRight())
										.count();
			if (sleepCount > maximumCount) {
				maximumCount = sleepCount;
				maximumMinute = currentMinute;
			}
		}

		return Pair.of(maximumMinute, maximumCount);
	}

	public long getScore() {
		return id * getMaximumSleepingMinute().getLeft();
	}
}
