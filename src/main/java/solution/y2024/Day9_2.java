package solution.y2024;

import java.util.ArrayList;
import java.util.List;

import utils.soution.Solution;

public class Day9_2 extends Solution {
	public static void main(String[] args) {
		new Day9_2().solve();
	}

	@Override
	protected String doSolve() {
		var fileSystem = createFileSystem();
		moveDataLeft(fileSystem);

		var checksum = 0L;
		for (int i = 1; i < fileSystem.size(); i++) {
			if (fileSystem.get(i) == null) {
				continue;
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
		for (int i = fileSystem.size() - 1; i >= 0; i--) {
			if (fileSystem.get(i) == null) {
				continue;
			}

			var indexStartOfFile = fileSystem.indexOf(fileSystem.get(i));
			var fileSize = i - indexStartOfFile + 1;
			var indexOfFirstFreeBlock = getIndexOfFirstFreeBlock(fileSystem, fileSize);

			i -= fileSize - 1;
			if (indexOfFirstFreeBlock < 0 || indexOfFirstFreeBlock > indexStartOfFile) {
				continue;
			}

			for (int j = 0; j < fileSize; j++) {
				fileSystem.set(indexOfFirstFreeBlock + j, fileSystem.get(indexStartOfFile + j));
				fileSystem.set(indexStartOfFile + j, null);
			}
		}
	}

	private int getIndexOfFirstFreeBlock(List<Integer> fileSystem, int size) {
		for (int i = 0; i < fileSystem.size() - size + 1; i++) {

			var valid = true;
			for (int j = i; j < i + size; j++) {
				if (fileSystem.get(j) != null) {
					valid = false;
					i = j;
					break;
				}
			}

			if (valid) {
				return i;
			}
		}

		return -1;
	}
}
