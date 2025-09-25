package solution.y2017;

import utils.Point;
import utils.soution.Solution;

public class Day3_1 extends Solution {
    public static void main(String[] args) {
        new Day3_1().solve();
    }

    @Override
    protected String doSolve() {
        var goal = Long.parseLong(input.getFirst());
        return getPointAtIndex(goal).computeManhattanDistance(new Point(0, 0)) + "";
    }

    private Point getPointAtIndex(long goal) {
        var current = 1L;
        var currentPoint = new Point(0, 0);

        for (int i = 1; true; i = i + 2) {
            var rest = goal - current;
            if (rest < i) {
                return new Point((int) (currentPoint.getX() + rest), currentPoint.getY());
            }
            current += i;
            currentPoint = new Point(currentPoint.getX() + i, currentPoint.getY());

            rest = goal - current;
            if (rest < i) {
                return new Point(currentPoint.getX(), (int) (currentPoint.getY() - rest));
            }
            current += i;
            currentPoint = new Point(currentPoint.getX(), currentPoint.getY() - i);

            rest = goal - current;
            if (rest <= i) {
                return new Point((int) (currentPoint.getX() - rest), currentPoint.getY());
            }
            current += i + 1;
            currentPoint = new Point(currentPoint.getX() - i - 1, currentPoint.getY());

            rest = goal - current;
            if (rest <= i) {
                return new Point(currentPoint.getX(), (int) (currentPoint.getY() + rest));
            }
            current += i + 1;
            currentPoint = new Point(currentPoint.getX(), currentPoint.getY() + i + 1);
        }
    }
}
