package solution.y2016;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.Pair;

import utils.Point;
import utils.soution.Solution;

public class Day17_2 extends Solution {
	private String passcode;

	public static void main(String[] args) {
		new Day17_2().solve();
	}

	@Override
	protected String doSolve() {
		passcode = input.getFirst();
		return findLongestPath(new Point(0, 0), new Point(3, 3)).length() + "";
	}

	private String findLongestPath(Point start, Point goal) {
		String solution = null;
		Queue<Pair<Point, String>> queue = new LinkedList<>();
		queue.add(Pair.of(start, ""));

		while (!queue.isEmpty()) {
			var currentStep = queue.remove();
			var currentPoint = currentStep.getLeft();
			var currentPath = currentStep.getRight();

			if (currentPoint.equals(goal)) {
				solution = currentPath;
				continue;
			}

			var hash = DigestUtils.md5Hex(passcode + currentPath);
			if (currentPoint.getY() > 0 && isOpen(hash.charAt(0))) {
				queue.add(Pair.of(currentPoint.getUpperNeighbour(), currentPath + 'U'));
			}
			if (currentPoint.getY() < goal.getY() && isOpen(hash.charAt(1))) {
				queue.add(Pair.of(currentPoint.getLowerNeighbour(), currentPath + 'D'));
			}
			if (currentPoint.getX() > 0 && isOpen(hash.charAt(2))) {
				queue.add(Pair.of(currentPoint.getLeftNeighbour(), currentPath + 'L'));
			}
			if (currentPoint.getX() < goal.getX() && isOpen(hash.charAt(3))) {
				queue.add(Pair.of(currentPoint.getRightNeighbour(), currentPath + 'R'));
			}
		}
		return solution;
	}

	private boolean isOpen(char c) {
		return c >= 'b' && c <= 'f';
	}
}
