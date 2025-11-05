package solution.y2023;

import java.util.ArrayList;
import java.util.List;

import utils.soution.Solution;

public class Day13_1 extends Solution {
	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected String doSolve() {
		return getTerrains().stream().mapToInt(this::getValue).sum() + "";
	}

	private List<List<String>> getTerrains() {
		var terrains = new ArrayList<List<String>>();
		var terrain = new ArrayList<String>();
		for (var line : input) {
			if (line.isEmpty()) {
				terrains.add(terrain);
				terrain = new ArrayList<>();
			} else {
				terrain.add(line);
			}
		}
		terrains.add(terrain);
		return terrains;
	}

	protected int getFaultCount() {
		return 0;
	}

	private int getValue(List<String> terrain) {
		for (var i = 0; i + 1 < terrain.size(); i++) {
			if (countReflectingErrorsInLine(terrain, i) == getFaultCount()) {
				return (i + 1) * 100;
			}
		}
		for (var i = 0; i + 1 < terrain.getFirst().length(); i++) {
			if (countReflectingErrorsInColumn(terrain, i) == getFaultCount()) {
				return i + 1;
			}
		}
		throw new IllegalStateException();
	}

	private int countReflectingErrorsInLine(List<String> terrain, int reflectingLineAfter) {
		var count = 0;
		for (var i = 0; i <= reflectingLineAfter && i < terrain.size() - reflectingLineAfter - 1; i++) {
			for (var j = 0; j < terrain.get(i).length(); j++) {
				if (terrain.get(reflectingLineAfter - i).charAt(j) != terrain	.get(reflectingLineAfter + i + 1)
																				.charAt(j)) {
					count++;
				}
			}
		}
		return count;
	}

	private int countReflectingErrorsInColumn(List<String> terrain, int reflectingColumnAfter) {
		var count = 0;
		for (var i = 0; i <= reflectingColumnAfter
				&& i < terrain.getFirst().length() - reflectingColumnAfter - 1; i++) {
			for (String s : terrain) {
				if (s.charAt(reflectingColumnAfter - i) != s.charAt(reflectingColumnAfter + i + 1)) {
					count++;
				}
			}
		}
		return count;
	}
}