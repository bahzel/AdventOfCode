package solution.y2024;

import java.util.ArrayList;

import utils.soution.Solution;

public class Day25_1 extends Solution {
	public static void main(String[] args) {
		new Day25_1().solve();
	}

	@Override
	protected String doSolve() {
		var locks = new ArrayList<int[]>();
		var keys = new ArrayList<int[]>();

		for (int i = 0; i < input.size(); i = i + 8) {
			if (input.get(i).startsWith("#")) {
				locks.add(getSchematic(i, '#'));
			} else {
				keys.add(getSchematic(i, '.'));
			}
		}

		var solution = 0;
		for (var lock : locks) {
			for (var key : keys) {
				if (key[0] >= lock[0] && key[1] >= lock[1] && key[2] >= lock[2] && key[3] >= lock[3]
						&& key[4] >= lock[4]) {
					solution++;
				}
			}
		}
		return solution + "";
	}

	private int[] getSchematic(int index, char symbol) {
		var schematic = new int[5];
		for (int column = 0; column < 5; column++) {
			for (int j = index + 5; j > index; j--) {
				if (input.get(j).charAt(column) == symbol) {
					schematic[column] = j - index;
					break;
				}
			}
		}
		return schematic;
	}
}
