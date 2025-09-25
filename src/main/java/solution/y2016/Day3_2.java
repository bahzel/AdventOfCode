package solution.y2016;

import org.apache.commons.lang3.tuple.Triple;
import utils.MapSolution;

import java.util.ArrayList;
import java.util.List;

public class Day3_2 extends MapSolution<List<Triple<Integer, Integer, Integer>>> {
	public static void main(String[] args) {
		new Day3_2().solve();
	}

	@Override
	protected List<Triple<Integer, Integer, Integer>> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Triple<Integer, Integer, Integer>> triples) {
		var lengths = instruction.replaceAll("\\s+", " ").substring(1).split(" ");
		triples.add(
				Triple.of(Integer.parseInt(lengths[0]), Integer.parseInt(lengths[1]), Integer.parseInt(lengths[2])));
	}

	@Override
	protected String computeSolution(List<Triple<Integer, Integer, Integer>> triples) {
		int validTriangles = 0;

		for (int i = 0; i < triples.size(); i = i + 3) {
			if (isValidTriangle(triples.get(i).getLeft(), triples.get(i + 1).getLeft(), triples.get(i + 2).getLeft())) {
				validTriangles++;
			}

			if (isValidTriangle(triples.get(i).getMiddle(), triples.get(i + 1).getMiddle(),
					triples.get(i + 2).getMiddle())) {
				validTriangles++;
			}

			if (isValidTriangle(triples.get(i).getRight(), triples.get(i + 1).getRight(),
					triples.get(i + 2).getRight())) {
				validTriangles++;
			}
		}

		return validTriangles + "";
	}

	private boolean isValidTriangle(int length1, int length2, int length3) {
		return length1 + length2 > length3 && length1 + length3 > length2 && length2 + length3 > length1;
	}
}
