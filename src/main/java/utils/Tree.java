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

	public List<? extends Tree<T>> getChildren() {
		return children;
	}

	public void addChild(Tree<T> child) {
		children.add(child);
		child.parent = this;
	}

	public int computeDepth() {
		if (parent == null) {
			return 0;
		} else {
			return parent.computeDepth() + 1;
		}
	}

	public boolean contains(T value) {
		if (this.value.equals(value)) {
			return true;
		}

		for (var child : children) {
			if (child.contains(value)) {
				return true;
			}
		}

		return false;
	}
}
