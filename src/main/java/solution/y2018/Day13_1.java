package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day13_1 extends GridSolution<Character> {
	private final List<Cart> carts = new ArrayList<>();

	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected GridElement<Character> transformCell(char ch, int x, int y) {
		var element = new GridElement<>(ch, x, y);
		return switch (ch) {
		case '>' -> {
			carts.add(new Cart(element, Direction.RIGHT));
			grid[x - 1][y].setRightNeighbour(element);
			element.setLeftNeighbour(grid[x - 1][y]);
			yield element;
		}
		case '<' -> {
			carts.add(new Cart(element, Direction.LEFT));
			grid[x - 1][y].setRightNeighbour(element);
			element.setLeftNeighbour(grid[x - 1][y]);
			yield element;
		}
		case 'v' -> {
			carts.add(new Cart(element, Direction.DOWN));
			grid[x][y - 1].setLowerNeighbour(element);
			element.setUpperNeighbour(grid[x][y - 1]);
			yield element;
		}
		case '^' -> {
			carts.add(new Cart(element, Direction.UP));
			grid[x][y - 1].setLowerNeighbour(element);
			element.setUpperNeighbour(grid[x][y - 1]);
			yield element;
		}
		case '|' -> {
			grid[x][y - 1].setLowerNeighbour(element);
			element.setUpperNeighbour(grid[x][y - 1]);
			yield element;
		}
		case '-' -> {
			grid[x - 1][y].setRightNeighbour(element);
			element.setLeftNeighbour(grid[x - 1][y]);
			yield element;
		}
		case '+' -> {
			grid[x - 1][y].setRightNeighbour(element);
			element.setLeftNeighbour(grid[x - 1][y]);
			grid[x][y - 1].setLowerNeighbour(element);
			element.setUpperNeighbour(grid[x][y - 1]);
			yield element;
		}
		case '\\' -> {
			if (x > 0 && (grid[x - 1][y].getValue() == '-' || grid[x - 1][y].getValue() == '+')) {
				grid[x - 1][y].setRightNeighbour(element);
				element.setLeftNeighbour(grid[x - 1][y]);
			}
			if (y > 0 && (grid[x][y - 1].getValue() == '|' || grid[x][y - 1].getValue() == '+')) {
				grid[x][y - 1].setLowerNeighbour(element);
				element.setUpperNeighbour(grid[x][y - 1]);
			}
			yield element;
		}
		case '/' -> {
			if (x > 0 && (grid[x - 1][y].getValue() == '-' || grid[x - 1][y].getValue() == '+')) {
				grid[x - 1][y].setRightNeighbour(element);
				element.setLeftNeighbour(grid[x - 1][y]);
				grid[x][y - 1].setLowerNeighbour(element);
				element.setUpperNeighbour(grid[x][y - 1]);
			}
			yield element;
		}
		case ' ' -> element;
		default -> throw new IllegalStateException("Unexpected value: " + ch);
		};
	}

	@Override
	protected void initializeNeighbours() {
		// overwrite
	}

	@Override
	protected String computeSolution() {
		while (true) {
			carts.sort((cart1,
					cart2) -> cart1.getPosition().getCoordinates().getY() == cart2.getPosition().getCoordinates().getY()
							? cart1.getPosition().getCoordinates().getX() - cart2.getPosition().getCoordinates().getX()
							: cart1.getPosition().getCoordinates().getY()
									- cart2.getPosition().getCoordinates().getY());
			for (var cart : carts) {
				cart.move();
				if (checkCollission(cart)) {
					return cart.getPosition().getCoordinates().format();
				}
			}
		}
	}

	private boolean checkCollission(Cart cart) {
		return carts.stream().filter(cart::equals).count() > 1;
	}
}

class Cart {
	@Getter
	private GridElement<Character> position;
	private Direction direction;
	private Direction nextDirection;

	public Cart(GridElement<Character> position, Direction direction) {
		this.position = position;
		this.direction = direction;
		nextDirection = Direction.LEFT;
	}

	public void move() {
		if (isIntersection()) {
			direction = direction.turn(nextDirection);
			computeNextDirection();
		}
		if (position.getNeighbour(direction) != null) {
			position = position.getNeighbour(direction);
		} else if (position.getNeighbour(direction.turn(Direction.LEFT)) != null) {
			direction = direction.turn(Direction.LEFT);
			position = position.getNeighbour(direction);
		} else if (position.getNeighbour(direction.turn(Direction.RIGHT)) != null) {
			direction = direction.turn(Direction.RIGHT);
			position = position.getNeighbour(direction);
		} else {
			throw new IllegalStateException();
		}
	}

	private boolean isIntersection() {
		return position.getRightNeighbour() != null && position.getUpperNeighbour() != null
				&& position.getLeftNeighbour() != null && position.getLowerNeighbour() != null;
	}

	private void computeNextDirection() {
		switch (nextDirection) {
		case UP:
			nextDirection = Direction.RIGHT;
			break;
		case RIGHT:
			nextDirection = Direction.LEFT;
			break;
		case LEFT:
			nextDirection = Direction.UP;
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + nextDirection);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cart)) {
			return false;
		}

		return position.getCoordinates().equals(((Cart) obj).position.getCoordinates());
	}
}
