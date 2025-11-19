package solution.y2023;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.Getter;
import utils.Point;
import utils.soution.GridElement;
import utils.soution.GridSolution;

public class Day21_2 extends PerformantGridSolution {
	private final Map<Point, PerformantGridSolution> grids = new HashMap<>();

	public static void main(String[] args) {
		new Day21_2().solve();
	}

	@Override
	public Stream<GridElement<GardenTile>> stream() {
		return grids.values().stream().flatMap(PerformantGridSolution::stream);
	}

	@Override
	protected String computeSolution() {
		grids.put(new Point(0, 0), clone(new Point(0, 0)));
		var start = stream().filter(tile -> tile.getValue() == GardenTile.Visited)
							.findAny()
							.orElseThrow()
							.getCoordinates();
		grid[start.getX()][start.getY()].setValue(GardenTile.Garden);

		for (var i = 0; i < 500; i = i + 2) {
			step();
		}
		return stream()	.filter(tile -> tile.getValue() == GardenTile.Visited || tile.getValue() == GardenTile.Handled)
						.count()
				+ "";
	}

	private void step() {
		var toVisit = new ArrayList<GridElement<GardenTile>>();
		for (var gridSolution : grids.values()) {
			toVisit.addAll(gridSolution.stream().filter(tile -> tile.getValue() == GardenTile.Visited).toList());
		}
		toVisit.forEach(this::step);
	}

	private void step(GridElement<GardenTile> tile) {
		var test = getBorderingNeighbours(
				(KnowingGridElement) tile)	.stream()
											.filter(neighbour -> neighbour.getValue() == GardenTile.Garden)
											.map(neighbour -> getBorderingNeighbours((KnowingGridElement) neighbour))
											.flatMap(Collection::stream)
											.filter(neighbour -> neighbour.getValue() == GardenTile.Garden)
											.toList();
		test.forEach(neighbour -> neighbour.setValue(GardenTile.Visited));
		tile.setValue(GardenTile.Handled);
	}

	private List<GridElement<GardenTile>> getBorderingNeighbours(KnowingGridElement element) {
		var neighbours = new ArrayList<GridElement<GardenTile>>();
		var x = element.getCoordinates().getX();
		var y = element.getCoordinates().getY();
		if (x > 0) {
			neighbours.add(element.getLeftNeighbour());
		} else {
			neighbours.add(grids.computeIfAbsent(element.getGrid().getCoordinates().getLeftNeighbour(), this::clone)
								.getGrid()[grid.length - 1][y]);
		}
		if (x < grid.length - 1) {
			neighbours.add(element.getRightNeighbour());
		} else {
			neighbours.add(grids.computeIfAbsent(element.getGrid().getCoordinates().getRightNeighbour(), this::clone)
								.getGrid()[0][y]);
		}
		if (y > 0) {
			neighbours.add(element.getUpperNeighbour());
		} else {
			neighbours.add(grids.computeIfAbsent(element.getGrid().getCoordinates().getUpperNeighbour(), this::clone)
								.getGrid()[x][grid[0].length - 1]);
		}
		if (y < grid[0].length - 1) {
			neighbours.add(element.getLowerNeighbour());
		} else {
			neighbours.add(grids.computeIfAbsent(element.getGrid().getCoordinates().getLowerNeighbour(), this::clone)
								.getGrid()[x][0]);
		}
		return neighbours;
	}
}

@Getter
class PerformantGridSolution extends GridSolution<GardenTile> {
	protected final Point coordinates;

	public PerformantGridSolution() {
		this.coordinates = null;
	}

	private PerformantGridSolution(PerformantGridSolution other, Point coordinates) {
		super(null);
		this.coordinates = coordinates;
		grid = new GridElement[other.grid.length][];
		for (int i = 0; i < other.grid.length; i++) {
			grid[i] = new GridElement[other.grid[i].length];
			for (int j = 0; j < other.grid[i].length; j++) {
				grid[i][j] = ((KnowingGridElement) other.grid[i][j]).clone(this);
			}
		}
		initializeNeighbours();
	}

	@Override
	protected GridElement<GardenTile> transformCell(char ch, int x, int y) {
		return switch (ch) {
		case '.' -> new KnowingGridElement(GardenTile.Garden, x, y);
		case '#' -> new KnowingGridElement(GardenTile.Rock, x, y);
		case 'S' -> new KnowingGridElement(GardenTile.Visited, x, y);
		default -> throw new IllegalArgumentException("Invalid character");
		};
	}

	@Override
	protected String computeSolution() {
		return "";
	}

	public PerformantGridSolution clone(Point point) {
		return new PerformantGridSolution(this, point);
	}

	public GridElement<GardenTile>[][] getGrid() {
		return grid;
	}
}

@Getter
class KnowingGridElement extends GridElement<GardenTile> {
	private final PerformantGridSolution grid;

	public KnowingGridElement(GardenTile value, int x, int y) {
		super(value, x, y);
		this.grid = null;
	}

	private KnowingGridElement(PerformantGridSolution grid, GardenTile value, Point point) {
		super(value, point);
		this.grid = grid;
	}

	public KnowingGridElement clone(PerformantGridSolution grid) {
		return new KnowingGridElement(grid, this.getValue(), this.getCoordinates());
	}
}