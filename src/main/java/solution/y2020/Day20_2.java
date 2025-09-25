package solution.y2020;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import utils.Point;
import utils.soution.Solution;

public class Day20_2 extends Solution {
	public static void main(String[] args) {
		new Day20_2().solve();
	}

	@Override
	protected String doSolve() {
		var images = getImages();
		var wholePicture = getWholePicture(images);
		var dragon = getDragon();

		var dragonCount = 0;
		while (dragonCount == 0) {
			for (var i = 0; i <= wholePicture.length - 3; i++) {
				for (var j = 0; j <= wholePicture[i].length - 20; j++) {
					if (isDragon(wholePicture, new Point(i, j), dragon)) {
						dragonCount++;
					}
				}
			}

			var flippedPicture = flip(wholePicture);
			for (var i = 0; i <= flippedPicture.length - 3; i++) {
				for (var j = 0; j <= flippedPicture[i].length - 20; j++) {
					if (isDragon(flippedPicture, new Point(i, j), dragon)) {
						dragonCount++;
					}
				}
			}

			wholePicture = rotate(wholePicture);
		}

		var pixel = 0;
		for (char[] chars : wholePicture) {
			for (char aChar : chars) {
				if (aChar == '#') {
					pixel++;
				}
			}
		}

		return pixel - dragonCount * dragon.size() + "";
	}

	private boolean isDragon(char[][] picture, Point coordinate, List<Point> dragon) {
		for (var point : dragon) {
			if (picture[coordinate.getX() + point.getX()][coordinate.getY() + point.getY()] != '#') {
				return false;
			}
		}
		return true;
	}

	private List<Point> getDragon() {
		return List.of(new Point(0, 18), new Point(1, 0), new Point(1, 5), new Point(1, 6), new Point(1, 11),
				new Point(1, 12), new Point(1, 17), new Point(1, 18), new Point(1, 19), new Point(2, 1),
				new Point(2, 4), new Point(2, 7), new Point(2, 10), new Point(2, 13), new Point(2, 16));
	}

	private char[][] getWholePicture(List<List<Image>> images) {
		var picture = new Image[(int) Math.sqrt(images.size())][(int) Math.sqrt(images.size())];
		picture[0][0] = getUpperLeftCorner(images);

		for (var i = 0; i < picture.length; i++) {
			for (var j = 0; j < picture[i].length; j++) {
				if (j == 0) {
					if (i == 0) {
						continue;
					}

					picture[i][j] = getLowerNeighbour(picture[i - 1][j], images);
				} else {
					picture[i][j] = getRightNeighbour(picture[i][j - 1], images);
				}
			}
		}

		var subImageSize = picture[0][0].getData().getFirst().length();
		var size = picture.length * subImageSize;
		var wholePicture = new char[size][size];
		for (var i = 0; i < size; i++) {
			for (var j = 0; j < size; j++) {
				wholePicture[i][j] = picture[i / subImageSize][j / subImageSize].getData()
																				.get(i % subImageSize)
																				.charAt(j % subImageSize);
			}
		}
		return wholePicture;
	}

	private Image getUpperLeftCorner(List<List<Image>> images) {
		for (var imageCluster : images) {
			var maxVerticalNeighbours = 0;
			for (var image : imageCluster) {
				var verticalNeighbours = 0;
				if (images	.stream()
							.flatMap(Collection::stream)
							.anyMatch(neighbour -> neighbour.getId() != image.getId()
									&& image.getLeft().equals(neighbour.getRight()))) {
					verticalNeighbours++;
				}
				if (images	.stream()
							.flatMap(Collection::stream)
							.anyMatch(neighbour -> neighbour.getId() != image.getId()
									&& image.getRight().equals(neighbour.getLeft()))) {
					verticalNeighbours++;
				}
				maxVerticalNeighbours = Math.max(maxVerticalNeighbours, verticalNeighbours);
			}

			if (maxVerticalNeighbours < 2) {
				for (var image : imageCluster) {
					if (getRightNeighbour(image, images) != null && getLowerNeighbour(image, images) != null) {
						return image;
					}
				}
			}
		}

		throw new IllegalStateException();
	}

	private Image getRightNeighbour(Image image, List<List<Image>> images) {
		for (var imageCluster : images) {
			if (imageCluster.getFirst().getId() == image.getId()) {
				continue;
			}

			for (var neighbour : imageCluster) {
				if (image.getRight().equals(neighbour.getLeft())) {
					return neighbour;
				}
			}
		}

		return null;
	}

	private Image getLowerNeighbour(Image image, List<List<Image>> images) {
		for (var imageCluster : images) {
			if (imageCluster.getFirst().getId() == image.getId()) {
				continue;
			}

			for (var neighbour : imageCluster) {
				if (image.getDown().equals(neighbour.getUp())) {
					return neighbour;
				}
			}
		}

		return null;
	}

	private List<List<Image>> getImages() {
		return ListUtils.partition(input, 12).stream().map(rawData -> {
			List<Image> rotatedImages = new ArrayList<>();
			var id = Integer.parseInt(rawData.getFirst().replace(":", "").split(" ")[1]);
			var data = rawData.subList(1, 11);

			rotatedImages.add(new Image(id, data));
			rotatedImages.add(new Image(id, flip(data)));
			var rotatedData = rotate(data);
			rotatedImages.add(new Image(id, rotatedData));
			rotatedImages.add(new Image(id, flip(rotatedData)));
			rotatedData = rotate(rotatedData);
			rotatedImages.add(new Image(id, rotatedData));
			rotatedImages.add(new Image(id, flip(rotatedData)));
			rotatedData = rotate(rotatedData);
			rotatedImages.add(new Image(id, rotatedData));
			rotatedImages.add(new Image(id, flip(rotatedData)));

			return rotatedImages;
		}).toList();
	}

	private List<String> rotate(List<String> image) {
		var rotatedList = new ArrayList<String>();
		for (int i = 0; i < image.size(); i++) {
			var line = new StringBuilder();
			for (int j = 0; j < image.size(); j++) {
				line.append(image.get(image.get(i).length() - j - 1).charAt(i));
			}
			rotatedList.add(line.toString());
		}
		return rotatedList;
	}

	private char[][] rotate(char[][] image) {
		var rotatedList = new char[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image.length; j++) {
				rotatedList[i][j] = image[image[i].length - j - 1][i];
			}
		}
		return rotatedList;
	}

	private List<String> flip(List<String> image) {
		var flippedList = new ArrayList<String>();
		for (var line : image) {
			flippedList.add(StringUtils.reverse(line));
		}
		return flippedList;
	}

	private char[][] flip(char[][] image) {
		var flippedList = new char[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image.length; j++) {
				flippedList[i][j] = image[i][image[i].length - j - 1];
			}
		}
		return flippedList;
	}
}
