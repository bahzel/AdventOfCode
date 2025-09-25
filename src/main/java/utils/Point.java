package utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Point {
    private int x;
    private int y;

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int computeDistance(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public Point getLeftNeighbour() {
        return new Point(x - 1, y);
    }

    public Point getRightNeighbour() {
        return new Point(x + 1, y);
    }

    public Point getUpperNeighbour() {
        return new Point(x, y - 1);
    }

    public Point getLowerNeighbour() {
        return new Point(x, y + 1);
    }
}
