package solution.y2018;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import utils.Point3D;
import utils.soution.MapSolution;

public class Day23_1 extends MapSolution<List<Pair<Point3D, Integer>>> {
	public static void main(String[] args) {
		new Day23_1().solve();
	}

	@Override
	protected List<Pair<Point3D, Integer>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Pair<Point3D, Integer>> pairs) {
		var instructions = instruction.replace("pos=<", "").replace(">, r=", ",").split(",");
		pairs.add(Pair.of(new Point3D(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				Integer.parseInt(instructions[2])), Integer.parseInt(instructions[3])));
	}

	@Override
	protected String computeSolution(List<Pair<Point3D, Integer>> pairs) {
		var strongestNanobot = pairs.stream().max(Comparator.comparingInt(Pair::getRight)).orElseThrow();
		return "" + pairs.stream()
						 .filter(nanobot -> nanobot.getLeft().computeManhattanDistance(strongestNanobot.getLeft())
								 <= strongestNanobot.getRight())
						 .count();
	}
}
