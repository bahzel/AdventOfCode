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
public class Point3D {
	private int x;
	private int y;
	private int z;

	public Point3D(Point3D other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public int computeManhattanDistance(Point3D other) {
		return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
	}
}
