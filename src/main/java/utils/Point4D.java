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
public class Point4D {
	private int x;
	private int y;
	private int z;
	private int t;

	public int computeManhattanDistance(Point4D other) {
		return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z) + Math.abs(t - other.t);
	}
}
