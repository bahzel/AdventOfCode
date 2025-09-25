package utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Network<T> {
    @Setter
    private T value;
    private final List<Network<T>> neighbours = new ArrayList<>();

    public Network(T value) {
        this.value = value;
    }

    public void addNeighbour(Network<T> child) {
        neighbours.add(child);
        child.neighbours.add(this);
    }
}
