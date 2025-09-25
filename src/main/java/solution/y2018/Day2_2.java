package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import utils.soution.MapSolution;

public class Day2_2 extends MapSolution<List<String>> {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected List<String> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<String> strings) {
		strings.add(instruction);
	}

	@Override
	protected String computeSolution(List<String> strings) {
		for (int i = 0; i < strings.size(); i++) {
			for (int j = i + 1; j < strings.size(); j++) {
				if (countDifferences(strings.get(i), strings.get(j)) == 1) {
					return getIntersection(strings.get(i), strings.get(j));
				}
			}
		}

		throw new RuntimeException("No solution found");
	}

	private int countDifferences(String id1, String id2) {
		var countDifferences = 0;

		for (int i = 0; i < id1.length(); i++) {
			if (id1.charAt(i) != id2.charAt(i)) {
				countDifferences++;
			}
		}

		return countDifferences;
	}

	private String getIntersection(String id1, String id2) {
		var solution = new StringBuilder();

		for (int i = 0; i < id1.length(); i++) {
			if (id1.charAt(i) == id2.charAt(i)) {
				solution.append(id1.charAt(i));
			}
		}

		return solution.toString();
	}
}
