package solution.y2021;

import utils.soution.Solution;

public class Day20_1 extends Solution {
	private final static int ROUNDS = 2;

	public static void main(String[] args) {
		new Day20_1().solve();
	}

	@Override
	protected String doSolve() {
		var algorithm = input.getFirst().split("");
		var image = getImage();
		for (var i = 0; i < ROUNDS; i++) {
			var nextImage = new boolean[image.length][image[0].length];
			for (var x = 0; x < nextImage.length; x++) {
				for (var y = 0; y < nextImage[0].length; y++) {
					var index = "";
					index += getValue(image, x - 1, y - 1, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x, y - 1, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x + 1, y - 1, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x - 1, y, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x, y, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x + 1, y, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x - 1, y + 1, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x, y + 1, i % 2 == 1) ? 1 : 0;
					index += getValue(image, x + 1, y + 1, i % 2 == 1) ? 1 : 0;
					if (algorithm[Integer.parseInt(index, 2)].equals("#")) {
						nextImage[x][y] = true;
					}
				}
			}
			image = nextImage;
		}

		var count = 0;
		for (var booleans : image) {
			for (var y = 0; y < image[0].length; y++) {
				if (booleans[y]) {
					count++;
				}
			}
		}
		return count + "";
	}

	private boolean getValue(boolean[][] image, int x, int y, boolean cornerValue) {
		if (x < 0 || x >= image.length || y < 0 || y >= image[0].length) {
			return cornerValue;
		}
		return image[x][y];
	}

	private boolean[][] getImage() {
		var image = new boolean[input.get(2).length() + 2 + 2 * ROUNDS][input.size() + 2 * ROUNDS];
		for (var y = 0; y < input.size() - 2; y++) {
			for (var x = 0; x < input.get(y + 2).length(); x++) {
				if (input.get(y + 2).charAt(x) == '#') {
					image[x + ROUNDS + 1][y + ROUNDS + 1] = true;
				}
			}
		}
		return image;
	}
}
