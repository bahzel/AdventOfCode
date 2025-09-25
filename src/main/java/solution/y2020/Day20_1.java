package solution.y2020;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import utils.soution.Solution;

public class Day20_1 extends Solution {
	public static void main(String[] args) {
		new Day20_1().solve();
	}

	@Override
	protected String doSolve() {
		var images = getImages();

		var solution = 1L;
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
				solution *= imageCluster.getFirst().getId();
			}
		}
		return solution + "";
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

	private List<String> flip(List<String> image) {
		var flippedList = new ArrayList<String>();
		for (var line : image) {
			flippedList.add(StringUtils.reverse(line));
		}
		return flippedList;
	}
}

@Getter
class Image {
	private final int id;
	private final List<String> data = new ArrayList<>();
	private final String up;
	private final String right;
	private final String down;
	private final String left;

	public Image(int id, List<String> data) {
		this.id = id;

		for (int i = 1; i < data.size() - 1; i++) {
			this.data.add(data.get(i).substring(1, data.get(i).length() - 1));
		}

		up = data.getFirst();
		down = data.getLast();
		StringBuilder leftEdge = new StringBuilder();
		StringBuilder rightEdge = new StringBuilder();
		for (var row : data) {
			leftEdge.append(row.charAt(0));
			rightEdge.append(row.charAt(row.length() - 1));
		}
		left = leftEdge.toString();
		right = rightEdge.toString();
	}
}
