package solution.y2024;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.tuple.Pair;
import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day21_2 extends InstructionSolution<List<String>, AtomicLong> {
	private final NumericalKeypadRobot numericalKeypadRobot = new NumericalKeypadRobot();
	private final ArrowKeypadRobot[] arrowKeypadRobots = new ArrowKeypadRobot[25];

	public static void main(String[] args) {
		new Day21_2().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		for (int i = 0; i < 25; i++) {
			arrowKeypadRobots[i] = new ArrowKeypadRobot();
		}
		return new AtomicLong();
	}

	@Override
	protected List<String> transformInstruction(String instruction) {
		return StringTransformer.splitString(instruction);
	}

	@Override
	protected boolean performInstruction(List<String> characters, AtomicLong atomicLong) {
		var sum = 0L;
		for (var character : characters) {
			sum += numericalKeypadRobot.moveTo(character)
									   .stream()
									   .mapToLong(direction -> getMinimumFromRobotMiddle(direction, 24))
									   .min()
									   .orElseThrow();
		}
		atomicLong.addAndGet(sum * Integer.parseInt(String.join("", characters).substring(0, 3)));

		return false;
	}

	private final Map<Pair<List<NumpadDirection>, Integer>, Long> cache = new HashMap<>();

	private long getMinimumFromRobotMiddle(List<NumpadDirection> directions, int index) {
		var key = Pair.of(directions, index);
		var cachedValue = cache.get(key);
		if (cachedValue != null) {
			return cachedValue;
		}

		var sum = 0L;
		for (var direction : directions) {
			if (index == 1) {
				sum += arrowKeypadRobots[index].moveTo(direction)
											   .stream()
											   .mapToLong(this::getMinimumFromRobotEnd)
											   .min()
											   .orElseThrow();
			} else {
				sum += arrowKeypadRobots[index].moveTo(direction)
											   .stream()
											   .mapToLong(newDirection -> getMinimumFromRobotMiddle(newDirection,
													   index - 1))
											   .min()
											   .orElseThrow();
			}
		}

		cache.put(key, sum);
		return sum;
	}

	private long getMinimumFromRobotEnd(List<NumpadDirection> directions) {
		return directions.stream().mapToLong(direction -> arrowKeypadRobots[0].moveToDistance(direction)).sum();
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}
