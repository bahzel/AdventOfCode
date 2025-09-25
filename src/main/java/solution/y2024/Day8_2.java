package solution.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Point;
import utils.soution.Solution;

public class Day8_2 extends Solution {
	public static void main(String[] args) {
		new Day8_2().solve();
	}

	@Override
	protected String doSolve() {
		var maxX = input.getFirst().length() - 1;
		var maxY = input.size() - 1;
		var antennaMap = createAntennaMap();
		var antiNodes = new HashSet<Point>();

		antennaMap.values().forEach(antennas -> antiNodes.addAll(computeAntiNodes(antennas, 0, maxX, 0, maxY)));

		return antiNodes.size() + "";
	}

	private Map<Character, List<Point>> createAntennaMap() {
		var antennaMap = new HashMap<Character, List<Point>>();

		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.getFirst().length(); x++) {
				if ('.' == input.get(y).charAt(x)) {
					continue;
				}
				antennaMap.computeIfAbsent(input.get(y).charAt(x), key -> new ArrayList<>()).add(new Point(x, y));
			}
		}

		return antennaMap;
	}

	private Set<Point> computeAntiNodes(List<Point> antennas, int minX, int maxX, int minY, int maxY) {
		var antiNodes = new HashSet<Point>();

		for (int i = 0; i < antennas.size() - 1; i++) {
			for (int j = i + 1; j < antennas.size(); j++) {
				int deltaX = antennas.get(i).getX() - antennas.get(j).getX();
				int deltaY = antennas.get(i).getY() - antennas.get(j).getY();

				for (int length = 0; true; length++) {
					int x = antennas.get(i).getX() + deltaX * length;
					int y = antennas.get(i).getY() + deltaY * length;
					if (x < minX || x > maxX || y < minY || y > maxY) {
						break;
					}
					antiNodes.add(new Point(x, y));
				}

				for (int length = 0; true; length++) {
					int x = antennas.get(j).getX() - deltaX * length;
					int y = antennas.get(j).getY() - deltaY * length;
					if (x < minX || x > maxX || y < minY || y > maxY) {
						break;
					}
					antiNodes.add(new Point(x, y));
				}
			}
		}

		return antiNodes;
	}
}
