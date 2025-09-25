package solution.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Point;
import utils.soution.Solution;

public class Day8_1 extends Solution {
	public static void main(String[] args) {
		new Day8_1().solve();
	}

	@Override
	protected String doSolve() {
		var maxX = input.getFirst().length() - 1;
		var maxY = input.size() - 1;
		var antennaMap = createAntennaMap();
		var antiNodes = new HashSet<Point>();

		antennaMap.values().forEach(antennas -> antiNodes.addAll(computeAntiNodes(antennas)));

		return antiNodes.stream()
						.filter(point -> point.getX() >= 0)
						.filter(point -> point.getX() <= maxX)
						.filter(point -> point.getY() >= 0)
						.filter(point -> point.getY() <= maxY)
						.count() + "";
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

	private Set<Point> computeAntiNodes(List<Point> antennas) {
		var antiNodes = new HashSet<Point>();

		for (int i = 0; i < antennas.size() - 1; i++) {
			for (int j = i + 1; j < antennas.size(); j++) {
				int deltaX = antennas.get(i).getX() - antennas.get(j).getX();
				int deltaY = antennas.get(i).getY() - antennas.get(j).getY();
				antiNodes.add(new Point(antennas.get(i).getX() + deltaX, antennas.get(i).getY() + deltaY));
				antiNodes.add(new Point(antennas.get(j).getX() - deltaX, antennas.get(j).getY() - deltaY));
			}
		}

		return antiNodes;
	}
}
