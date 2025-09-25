package solution.y2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Triple;
import utils.Point;
import utils.soution.InstructionSolution;

public class Day3_2 extends InstructionSolution<Fabric, Triple<Set<Point>, Set<Point>, List<Fabric>>> {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected Triple<Set<Point>, Set<Point>, List<Fabric>> initializeValue() {
		return Triple.of(new HashSet<>(), new HashSet<>(), new ArrayList<>());
	}

	@Override
	protected Fabric transformInstruction(String instruction) {
		var instructions = instruction.replace(":", "").split(" ");
		var distance = instructions[2].split(",");
		var size = instructions[3].split("x");
		var distanceX = Integer.parseInt(distance[0]);
		var distanceY = Integer.parseInt(distance[1]);
		var sizeX = Integer.parseInt(size[0]);
		var sizeY = Integer.parseInt(size[1]);
		var fabric = new Fabric(instructions[0].substring(1));

		for (var x = 0; x < sizeX; x++) {
			for (var y = 0; y < sizeY; y++) {
				fabric.addPoint(new Point(x + distanceX, y + distanceY));
			}
		}

		return fabric;
	}

	@Override
	protected boolean performInstruction(Fabric fabric, Triple<Set<Point>, Set<Point>, List<Fabric>> setSetPair) {
		for (var point : fabric.getPoints()) {
			if (setSetPair.getMiddle().contains(point)) {
				continue;
			}
			if (setSetPair.getLeft().remove(point)) {
				setSetPair.getMiddle().add(point);
			} else {
				setSetPair.getLeft().add(point);
			}
		}
		setSetPair.getRight().add(fabric);

		return false;
	}

	@Override
	protected String getSolution(Triple<Set<Point>, Set<Point>, List<Fabric>> setSetPair) {
		for (var fabric : setSetPair.getRight()) {
			if (setSetPair.getLeft().containsAll(fabric.getPoints())) {
				return fabric.getId();
			}
		}

		throw new RuntimeException("No solution found");
	}
}

@Getter
class Fabric {
	private final String id;
	private final List<Point> points;

	public Fabric(String id) {
		this.id = id;
		this.points = new ArrayList<>();
	}

	public void addPoint(Point point) {
		points.add(point);
	}
}
