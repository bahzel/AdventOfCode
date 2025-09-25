package utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GridElement<T> {
	private T value;
	private T nextValue;
	private List<GridElement<T>> neighbours;

	public GridElement() {
	}

	public GridElement(T value) {
		this.value = value;
	}

	public void assignNextValue() {
		value = nextValue;
		nextValue = null;
	}
}
