package utils.soution;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import utils.Direction;
import utils.Point;

@Getter
public class GridElement<T> {
	@Setter
	private T value;
	@Setter
	private T nextValue;
	private final Point coordinates;
	private GridElement<T> leftNeighbour;
	private GridElement<T> rightNeighbour;
	private GridElement<T> upperNeighbour;
	private GridElement<T> lowerNeighbour;
	private final List<GridElement<T>> allNeighbours = new ArrayList<>();
	private final List<GridElement<T>> borderingNeighbours = new ArrayList<>();

	public GridElement(T value, int x, int y) {
		this.value = value;
		this.coordinates = new Point(x, y);
	}

	public GridElement(T value, Point coordinates) {
		this.value = value;
		this.coordinates = coordinates;
	}

	public void assignNextValue() {
		value = nextValue;
		nextValue = null;
	}

	public GridElement<T> setLeftNeighbour(GridElement<T> neighbour) {
		leftNeighbour = neighbour;
		allNeighbours.add(neighbour);
		borderingNeighbours.add(neighbour);
		return neighbour;
	}

	public GridElement<T> setRightNeighbour(GridElement<T> neighbour) {
		rightNeighbour = neighbour;
		allNeighbours.add(neighbour);
		borderingNeighbours.add(neighbour);
		return neighbour;
	}

	public GridElement<T> setUpperNeighbour(GridElement<T> neighbour) {
		upperNeighbour = neighbour;
		allNeighbours.add(neighbour);
		borderingNeighbours.add(neighbour);
		return neighbour;
	}

	public GridElement<T> setLowerNeighbour(GridElement<T> neighbour) {
		lowerNeighbour = neighbour;
		allNeighbours.add(neighbour);
		borderingNeighbours.add(neighbour);
		return neighbour;
	}

	public GridElement<T> setNeighbour(Direction direction, T value) {
		return switch (direction) {
			case UP -> {
				var neighbour = setUpperNeighbour(new GridElement<>(value, coordinates.getX(), coordinates.getY() - 1));
				neighbour.setLowerNeighbour(this);
				yield neighbour;
			}
			case DOWN -> {
				var neighbour = setLowerNeighbour(new GridElement<>(value, coordinates.getX(), coordinates.getY() + 1));
				neighbour.setUpperNeighbour(this);
				yield neighbour;
			}
			case LEFT -> {
				var neighbour = setLeftNeighbour(new GridElement<>(value, coordinates.getX() - 1, coordinates.getY()));
				neighbour.setRightNeighbour(this);
				yield neighbour;
			}
			case RIGHT -> {
				var neighbour = setRightNeighbour(new GridElement<>(value, coordinates.getX() + 1, coordinates.getY()));
				neighbour.setLeftNeighbour(this);
				yield neighbour;
			}
		};
	}

	public GridElement<T> getNeighbour(Direction direction) {
		return switch (direction) {
			case UP -> getUpperNeighbour();
			case DOWN -> getLowerNeighbour();
			case LEFT -> getLeftNeighbour();
			case RIGHT -> getRightNeighbour();
		};
	}

	@Override
	public String toString() {
		return coordinates.toString() + " value=" + value;
	}
}
