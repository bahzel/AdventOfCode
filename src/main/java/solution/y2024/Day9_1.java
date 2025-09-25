package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import utils.soution.Solution;

public class Day9_1 extends Solution {
	public static void main(String[] args) {
		new Day9_1().solve();
	}

	@Override
	protected String doSolve() {
		var fileSystem = createFileSystem();
		moveDataLeft(fileSystem);

		var checksum = 0L;
		for (int i = 1; i < fileSystem.size(); i++) {
			if (fileSystem.get(i) == null) {
				break;
			}

			checksum += (long) i * fileSystem.get(i);
		}

		return checksum + "";
	}

	private List<Integer> createFileSystem() {
		var inputLine = input.getFirst();
		var fileSystem = new ArrayList<Integer>();

		var amountFirstIndex = Integer.parseInt(inputLine.substring(0, 1));
		for (int i = 0; i < amountFirstIndex; i++) {
			fileSystem.add(0);
		}

		for (int i = 1; i * 2 < inputLine.length(); i++) {
			var amountEmpty = Integer.parseInt(inputLine.substring(i * 2 - 1, i * 2));
			for (int j = 0; j < amountEmpty; j++) {
				fileSystem.add(null);
			}

			var amountData = Integer.parseInt(inputLine.substring(i * 2, i * 2 + 1));
			for (int j = 0; j < amountData; j++) {
				fileSystem.add(i);
			}
		}

		return fileSystem;
	}

	private void moveDataLeft(List<Integer> fileSystem) {
		var indexRight = fileSystem.size() - 1;
		for (int i = 0; i < fileSystem.size(); i++) {
			if (fileSystem.get(i) != null) {
				continue;
			}

			while (indexRight >= 0 && fileSystem.get(indexRight) == null) {
				indexRight--;
			}

			if (indexRight < i) {
				return;
			}

			fileSystem.set(i, fileSystem.get(indexRight));
			fileSystem.set(indexRight, null);
		}
	}
}
