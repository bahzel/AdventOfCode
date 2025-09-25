package solution.y2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.Booleans;
import utils.soution.MapSolution;

public class Day21_2 extends MapSolution<Map<Square, boolean[][]>> {
	public static void main(String[] args) {
		new Day21_2().solve();
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

		Map<Square, boolean[][]> newMap = new HashMap<>();

		newMap.put(new Square(key), value);
		for (int i = 0; i < 3; i++) {
			key = rotate(key);
			newMap.put(new Square(key), value);
		}

		if (key[0].length == 3) {
			key = flipVertical(key);
			newMap.put(new Square(key), value);
			for (int i = 0; i < 3; i++) {
				key = rotate(key);
				newMap.put(new Square(key), value);
			}
		}

		var sizeOld = map.size();
		var sizeNew = newMap.size();
		map.putAll(newMap);

		if (map.size() != sizeOld + sizeNew) {
			throw new IllegalStateException();
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
		boolean[][] pixles = new boolean[3][3];
		pixles[0][0] = false;
		pixles[1][0] = true;
		pixles[2][0] = false;
		pixles[0][1] = false;
		pixles[1][1] = false;
		pixles[2][1] = true;
		pixles[0][2] = true;
		pixles[1][2] = true;
		pixles[2][2] = true;

		for (int i = 0; i < 18; i++) {
			if (pixles.length % 2 == 0) {
				pixles = enhanceDivisible2(pixles, map);
			} else {
				pixles = enhanceDivisible3(pixles, map);
			}
		}

		return "" + Arrays.stream(pixles).flatMap(Booleans::stream).filter(value -> value).count();
	}

	private boolean[][] enhanceDivisible2(boolean[][] pixles, Map<Square, boolean[][]> map) {
		boolean[][] newPixles = new boolean[pixles.length + pixles.length / 2][pixles.length + pixles.length / 2];

		for (int i = 0; i < pixles.length / 2; i++) {
			for (int j = 0; j < pixles.length / 2; j++) {
				var value = map.get(getSquare(pixles, i * 2, j * 2, 2));
				newPixles[i * 3][j * 3] = value[0][0];
				newPixles[i * 3 + 1][j * 3] = value[1][0];
				newPixles[i * 3 + 2][j * 3] = value[2][0];
				newPixles[i * 3][j * 3 + 1] = value[0][1];
				newPixles[i * 3 + 1][j * 3 + 1] = value[1][1];
				newPixles[i * 3 + 2][j * 3 + 1] = value[2][1];
				newPixles[i * 3][j * 3 + 2] = value[0][2];
				newPixles[i * 3 + 1][j * 3 + 2] = value[1][2];
				newPixles[i * 3 + 2][j * 3 + 2] = value[2][2];
			}
		}

		return newPixles;
	}

	private boolean[][] enhanceDivisible3(boolean[][] pixles, Map<Square, boolean[][]> map) {
		boolean[][] newPixles = new boolean[pixles.length + pixles.length / 3][pixles.length + pixles.length / 3];

		for (int i = 0; i < pixles.length / 3; i++) {
			for (int j = 0; j < pixles.length / 3; j++) {
				var value = map.get(getSquare(pixles, i * 3, j * 3, 3));
				newPixles[i * 4][j * 4] = value[0][0];
				newPixles[i * 4 + 1][j * 4] = value[1][0];
				newPixles[i * 4 + 2][j * 4] = value[2][0];
				newPixles[i * 4 + 3][j * 4] = value[3][0];
				newPixles[i * 4][j * 4 + 1] = value[0][1];
				newPixles[i * 4 + 1][j * 4 + 1] = value[1][1];
				newPixles[i * 4 + 2][j * 4 + 1] = value[2][1];
				newPixles[i * 4 + 3][j * 4 + 1] = value[3][1];
				newPixles[i * 4][j * 4 + 2] = value[0][2];
				newPixles[i * 4 + 1][j * 4 + 2] = value[1][2];
				newPixles[i * 4 + 2][j * 4 + 2] = value[2][2];
				newPixles[i * 4 + 3][j * 4 + 2] = value[3][2];
				newPixles[i * 4][j * 4 + 3] = value[0][3];
				newPixles[i * 4 + 1][j * 4 + 3] = value[1][3];
				newPixles[i * 4 + 2][j * 4 + 3] = value[2][3];
				newPixles[i * 4 + 3][j * 4 + 3] = value[3][3];
			}
		}

		return newPixles;
	}

	private Square getSquare(boolean[][] pixles, int x, int y, int size) {
		var square = new boolean[size][size];

		for (int i = 0; i < size; i++) {
			System.arraycopy(pixles[i + x], y, square[i], 0, size);
		}

		return new Square(square);
	}
}
