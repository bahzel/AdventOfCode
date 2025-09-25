package solution.y2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.AllArgsConstructor;
import utils.Booleans;
import utils.soution.MapSolution;

public class Day21_1 extends MapSolution<Map<Square, boolean[][]>> {
	public static void main(String[] args) {
		new Day21_1().solve();
	}

	@Override
	protected Map<Square, boolean[][]> initializeMapping() {
		return new HashMap<>();
	}

	@Override
	protected void transformInstruction(String instruction, Map<Square, boolean[][]> map) {
		var instructions = instruction.split(" => ");
		var key = map(instructions[0].split("/"));
		var value = map(instructions[1].split("/"));

		map.put(new Square(key), value);
		for (int i = 0; i < 3; i++) {
			key = rotate(key);
			map.put(new Square(key), value);
		}

		if (key[0].length == 3) {
			key = flipVertical(key);
			map.put(new Square(key), value);
			for (int i = 0; i < 3; i++) {
				key = rotate(key);
				map.put(new Square(key), value);
			}
		}
	}

	private boolean[][] map(String[] instructions) {
		var value = new boolean[instructions.length][instructions.length];

		for (int i = 0; i < instructions.length; i++) {
			for (int j = 0; j < instructions.length; j++) {
				value[j][i] = instructions[i].charAt(j) == '#';
			}
		}

		return value;
	}

	private boolean[][] rotate(boolean[][] arr) {
		var copy = new boolean[arr.length][arr.length];
		copy[0][0] = arr[0][arr.length - 1];
		copy[0][arr.length - 1] = arr[arr.length - 1][arr.length - 1];
		copy[arr.length - 1][arr.length - 1] = arr[arr.length - 1][0];
		copy[arr.length - 1][0] = arr[0][0];

		if (arr.length > 2) {
			copy[1][0] = arr[0][1];
			copy[0][1] = arr[1][2];
			copy[1][2] = arr[2][1];
			copy[2][1] = arr[1][0];
			copy[1][1] = arr[1][1];
		}

		return copy;
	}

	private boolean[][] flipVertical(boolean[][] arr) {
		var copy = new boolean[arr.length][arr.length];
		copy[0][0] = arr[2][0];
		copy[1][0] = arr[1][0];
		copy[2][0] = arr[0][0];
		copy[0][1] = arr[2][1];
		copy[1][1] = arr[1][1];
		copy[2][1] = arr[0][1];
		copy[0][2] = arr[2][2];
		copy[1][2] = arr[1][2];
		copy[2][2] = arr[0][2];

		return copy;
	}

	@Override
	protected String computeSolution(Map<Square, boolean[][]> map) {
		List<List<Boolean>> pixles = List.of(List.of(false, true, false), List.of(false, false, true),
				List.of(true, true, true));

		for (int i = 0; i < 5; i++) {
			if (pixles.size() % 2 == 0) {
				pixles = enhanceDivisible2(pixles, map);
			} else {
				pixles = enhanceDivisible3(pixles, map);
			}
		}

		return "" + pixles.stream().flatMap(Collection::stream).filter(value -> value).count();
	}

	private List<List<Boolean>> enhanceDivisible2(List<List<Boolean>> pixles, Map<Square, boolean[][]> map) {
		List<List<Boolean>> newPixles = new ArrayList<>();

		for (int i = 0; i < pixles.size(); i = i + 2) {
			List<Boolean> row1 = new ArrayList<>();
			List<Boolean> row2 = new ArrayList<>();
			List<Boolean> row3 = new ArrayList<>();
			newPixles.add(row1);
			newPixles.add(row2);
			newPixles.add(row3);
			for (int j = 0; j < pixles.get(i).size(); j = j + 2) {
				var value = map.get(getSquare(pixles, j, i, 2));
				row1.add(value[0][0]);
				row1.add(value[1][0]);
				row1.add(value[2][0]);
				row2.add(value[0][1]);
				row2.add(value[1][1]);
				row2.add(value[2][1]);
				row3.add(value[0][2]);
				row3.add(value[1][2]);
				row3.add(value[2][2]);
			}
		}

		return newPixles;
	}

	private List<List<Boolean>> enhanceDivisible3(List<List<Boolean>> pixles, Map<Square, boolean[][]> map) {
		List<List<Boolean>> newPixles = new ArrayList<>();

		for (int i = 0; i < pixles.size(); i = i + 3) {
			List<Boolean> row1 = new ArrayList<>();
			List<Boolean> row2 = new ArrayList<>();
			List<Boolean> row3 = new ArrayList<>();
			List<Boolean> row4 = new ArrayList<>();
			newPixles.add(row1);
			newPixles.add(row2);
			newPixles.add(row3);
			newPixles.add(row4);
			for (int j = 0; j < pixles.get(i).size(); j = j + 3) {
				var value = map.get(getSquare(pixles, j, i, 3));
				row1.add(value[0][0]);
				row1.add(value[1][0]);
				row1.add(value[2][0]);
				row1.add(value[3][0]);
				row2.add(value[0][1]);
				row2.add(value[1][1]);
				row2.add(value[2][1]);
				row2.add(value[3][1]);
				row3.add(value[0][2]);
				row3.add(value[1][2]);
				row3.add(value[2][2]);
				row3.add(value[3][2]);
				row4.add(value[0][3]);
				row4.add(value[1][3]);
				row4.add(value[2][3]);
				row4.add(value[3][3]);
			}
		}

		return newPixles;
	}

	private Square getSquare(List<List<Boolean>> pixles, int x, int y, int size) {
		var square = new boolean[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				square[i][j] = pixles.get(i + x).get(j + y);
			}
		}

		return new Square(square);
	}
}

@AllArgsConstructor
class Square {
	private final boolean[][] square;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Square other)) {
			return false;
		}

		if (square.length != other.square.length) {
			return false;
		}

		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square.length; j++) {
				if (square[i][j] != other.square[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.stream(square).flatMap(Booleans::stream).toArray());
	}
}
