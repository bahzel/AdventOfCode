package utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Tree<T> {
    @Setter
    private T value;
    private Tree<T> parent;
    private final List<Tree<T>> children = new ArrayList<>();

    public Tree() {
    }

    public Tree(T value) {
        this.value = value;
    }

    public void addChild(Tree<T> child) {
        children.add(child);
        child.parent = this;
    }
}
