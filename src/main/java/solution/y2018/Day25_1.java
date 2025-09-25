package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import utils.Point4D;
import utils.soution.MapSolution;

public class Day25_1 extends MapSolution<List<Point4D>> {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected List<Point4D> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Point4D> point4DS) {
		var instructions = instruction.split(",");
		point4DS.add(new Point4D(Integer.parseInt(instructions[0]), Integer.parseInt(instructions[1]),
				Integer.parseInt(instructions[2]), Integer.parseInt(instructions[3])));
	}

	@Override
	protected String computeSolution(List<Point4D> point4DS) {
		var solution = 0;

		while (!point4DS.isEmpty()) {
			var constellation = new ArrayList<Point4D>();
			constellation.add(point4DS.getFirst());
			point4DS.removeFirst();

			for (int i = 0; i < constellation.size(); i++) {
				for (int j = 0; j < point4DS.size(); j++) {
					if (constellation.get(i).computeManhattanDistance(point4DS.get(j)) <= 3) {
						constellation.add(point4DS.get(j));
						point4DS.remove(j);
						j--;
					}
				}
			}

			solution++;
		}

		return solution + "";
	}
}
