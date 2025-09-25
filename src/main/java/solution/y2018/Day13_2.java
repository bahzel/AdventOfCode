package solution.y2018;

import java.util.ArrayList;
import java.util.List;

import utils.Direction;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day13_2 extends GridSolution<Character> {
	private final List<Cart> carts = new ArrayList<>();

	public static void main(String[] args) {
		new Day13_2().solve();
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
		var crashedCarts = new ArrayList<Cart>();

		while (true) {
			carts.sort((cart1,
					cart2) -> cart1.getPosition().getCoordinates().getY() == cart2.getPosition().getCoordinates().getY()
							? cart1.getPosition().getCoordinates().getX() - cart2.getPosition().getCoordinates().getX()
							: cart1.getPosition().getCoordinates().getY()
									- cart2.getPosition().getCoordinates().getY());
			for (var cart : carts) {
				cart.move();
				if (checkCollission(cart)) {
					crashedCarts.addAll(carts.stream().filter(cart::equals).toList());
				}
			}

			carts.removeAll(crashedCarts);
			crashedCarts.clear();
			if (carts.size() < 2) {
				return carts.getFirst().getPosition().getCoordinates().format();
			}
		}
	}

	private boolean checkCollission(Cart cart) {
		return carts.stream().filter(cart::equals).count() > 1;
	}
}
