package solution.y2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<List<Point>, Pair<Set<Point>, Set<Point>>> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected Pair<Set<Point>, Set<Point>> initializeValue() {
		return Pair.of(new HashSet<>(), new HashSet<>());
	}

	@Override
	protected List<Point> transformInstruction(String instruction) {
		var instructions = instruction.replace(":", "").split(" ");
		var distance = instructions[2].split(",");
		var size = instructions[3].split("x");
		var distanceX = Integer.parseInt(distance[0]);
		var distanceY = Integer.parseInt(distance[1]);
		var sizeX = Integer.parseInt(size[0]);
		var sizeY = Integer.parseInt(size[1]);
		var points = new ArrayList<Point>();

		for (var x = 0; x < sizeX; x++) {
			for (var y = 0; y < sizeY; y++) {
				points.add(new Point(x + distanceX, y + distanceY));
			}
		}

		return points;
	}

	@Override
	protected boolean performInstruction(List<Point> points, Pair<Set<Point>, Set<Point>> setSetPair) {
		for (var point : points) {
			if (setSetPair.getRight().contains(point)) {
				continue;
			}
			if (setSetPair.getLeft().remove(point)) {
				setSetPair.getRight().add(point);
			} else {
				setSetPair.getLeft().add(point);
			}
		}

		return false;
	}

	@Override
	protected String getSolution(Pair<Set<Point>, Set<Point>> setSetPair) {
		return setSetPair.getRight().size() + "";
	}
}
