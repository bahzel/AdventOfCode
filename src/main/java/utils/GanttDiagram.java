package utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GanttDiagram<T> {
	@Setter
	private T value;
	private final List<GanttDiagram<T>> parents = new ArrayList<>();
	private final List<GanttDiagram<T>> children = new ArrayList<>();

	public GanttDiagram(T value) {
		this.value = value;
	}

	public void addChild(GanttDiagram<T> child) {
		children.add(child);
		child.parents.add(this);
	}
}
