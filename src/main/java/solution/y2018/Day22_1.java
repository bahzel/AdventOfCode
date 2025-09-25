package solution.y2018;

import utils.soution.GridElement;
import utils.soution.GridInstruction;

public class Day22_1 extends GridInstruction<Long, String> {
	public static void main(String[] args) {
		new Day22_1().solve();
	}

	@Override
	protected int getWidth() {
		return Integer.parseInt(input.get(1).split(" ")[1].split(",")[0]) + 1;
	}

	@Override
	protected int getHeigth() {
		return Integer.parseInt(input.get(1).split(" ")[1].split(",")[1]) + 1;
	}

	@Override
	protected Long initialValue() {
		return 0L;
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected void performInstruction(String s, GridElement<Long>[][] grid) {
	}

	@Override
	protected String getSolution(GridElement<Long>[][] grid) {
		var depth = Integer.parseInt(input.getFirst().split(" ")[1]);

		for (var y = 0; y < grid[0].length; y++) {
			for (var x = 0; x < grid.length; x++) {
				long geolocicalIndex;
				if (x == 0 && y == 0) {
					geolocicalIndex = 0;
				} else if (x == grid.length - 1 && y == grid[0].length - 1) {
					geolocicalIndex = 0;
				} else if (y == 0) {
					geolocicalIndex = x * 16807L;
				} else if (x == 0) {
					geolocicalIndex = y * 48271L;
				} else {
					geolocicalIndex = grid[x - 1][y].getValue() * grid[x][y - 1].getValue();
				}
				grid[x][y].setValue((geolocicalIndex + depth) % 20183);
			}
		}

		return stream().mapToLong(element -> element.getValue() % 3).sum() + "";
	}
}
