package utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
    private final List<GridElement<T>> neighbours = new ArrayList<>();
	
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

    public void setLeftNeighbour(GridElement<T> neighbour) {
        leftNeighbour = neighbour;
        neighbours.add(neighbour);
    }

    public void setRightNeighbour(GridElement<T> neighbour) {
        rightNeighbour = neighbour;
        neighbours.add(neighbour);
    }

    public void setUpperNeighbour(GridElement<T> neighbour) {
        upperNeighbour = neighbour;
        neighbours.add(neighbour);
    }

    public void setLowerNeighbour(GridElement<T> neighbour) {
        lowerNeighbour = neighbour;
        neighbours.add(neighbour);
    }
}
