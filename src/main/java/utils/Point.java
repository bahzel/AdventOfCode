package utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
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
}
