package utils;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Network<T> {
	@Setter
	private T value;
	private final Set<Network<T>> neighbours = new HashSet<>();

	public Network(T value) {
		this.value = value;
	}

	public void addNeighbour(Network<T> child) {
		neighbours.add(child);
		child.neighbours.add(this);
	}
}
