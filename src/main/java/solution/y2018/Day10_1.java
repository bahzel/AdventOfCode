package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.Point;
import utils.soution.MapSolution;

public class Day10_1 extends MapSolution<List<Star>> {
	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected List<Star> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Star> stars) {
		var instructions = instruction	.replace("position=<", "")
										.replace("> velocity=<", ", ")
										.replace(">", "")
										.split(", ");
		stars.add(
				new Star(new Point(Integer.parseInt(instructions[0].trim()), Integer.parseInt(instructions[1].trim())),
						new Point(Integer.parseInt(instructions[2].trim()), Integer.parseInt(instructions[3].trim()))));
	}

	@Override
	protected String computeSolution(List<Star> stars) {
		var secondsUntilPositive = stars.getFirst().getPosition().getX() / stars.getFirst().getVelocity().getX();
		stars.forEach(star -> star.addSeconds(secondsUntilPositive));

		while (stars.stream().anyMatch(star -> star.getPosition().getX() < 0 || star.getPosition().getY() < 0)) {
			stars.forEach(Star::addSecond);
		}

		var currentMessage = printMessage(stars);
		while (true) {
			stars.forEach(Star::addSecond);
			var nextMessage = printMessage(stars);
			if (nextMessage.length() < currentMessage.length()) {
				currentMessage = nextMessage;
			} else {
				return currentMessage;
			}
		}
	}

	private String printMessage(List<Star> stars) {
		var xMin = stars.stream().mapToInt(star -> star.getPosition().getX()).min().orElseThrow();
		var xMax = stars.stream().mapToInt(star -> star.getPosition().getX()).max().orElseThrow();
		var yMin = stars.stream().mapToInt(star -> star.getPosition().getY()).min().orElseThrow();
		var yMax = stars.stream().mapToInt(star -> star.getPosition().getY()).max().orElseThrow();
		var message = new StringBuilder();
		var picture = new boolean[yMax - yMin + 1][xMax - xMin + 1];

		stars.forEach(star -> picture[star.getPosition().getY() - yMin][star.getPosition().getX() - xMin] = true);

		for (boolean[] booleans : picture) {
			for (boolean aBoolean : booleans) {
				if (aBoolean) {
					message.append('#');
				} else {
					message.append(' ');
				}
			}
			message.append('\n');
		}

		return message.toString();
	}
}

@Getter
@AllArgsConstructor
class Star {
	private final Point position;
	private final Point velocity;

	public void addSeconds(int seconds) {
		position.setX(position.getX() + seconds * velocity.getX());
		position.setY(position.getY() + seconds * velocity.getY());
	}

	public void addSecond() {
		addSeconds(1);
	}
}
