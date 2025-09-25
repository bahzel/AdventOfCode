package solution.y2022;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.Tree;
import utils.soution.InstructionSolution;

public class Day7_1 extends InstructionSolution<String, AtomicReference<FileObject>> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected AtomicReference<FileObject> initializeValue() {
		return new AtomicReference<>();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String s, AtomicReference<FileObject> fileObjectAtomicReference) {
		var instruction = s.split(" ");
		switch (instruction[0]) {
		case "$":
			if ("ls".equals(instruction[1])) {
				return false;
			} else if ("/".equals(instruction[2])) {
				fileObjectAtomicReference.set(new Folder("/"));
			} else if ("..".equals(instruction[2])) {
				fileObjectAtomicReference.set(fileObjectAtomicReference.get().getParent());
			} else {
				fileObjectAtomicReference.set(
						fileObjectAtomicReference	.get()
													.getChildren()
													.stream()
													.filter(child -> child.getName().equals(instruction[2]))
													.findAny()
													.orElseThrow());
			}
			break;
		case "dir":
			fileObjectAtomicReference.get().addChild(new Folder(instruction[1]));
			break;
		default:
			fileObjectAtomicReference.get().addChild(new File(instruction[1], Long.parseLong(instruction[0])));
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicReference<FileObject> fileObjectAtomicReference) {
		var parent = fileObjectAtomicReference.get();
		while (parent.getParent() != null) {
			parent = parent.getParent();
		}

		var solution = 0L;
		Queue<FileObject> queue = new LinkedList<>();
		queue.add(parent);
		while (!queue.isEmpty()) {
			var object = queue.poll();
			if (object instanceof Folder) {
				queue.addAll(object.getChildren());
			} else {
				continue;
			}

			var size = object.getSize();
			if (size <= 100000) {
				solution += size;
			}
		}

		return solution + "";
	}
}

@AllArgsConstructor
@Getter
abstract class FileObject extends Tree<FileObject> {
	private final String name;

	@Override
	public FileObject getParent() {
		return (FileObject) super.getParent();
	}

	@Override
	public List<FileObject> getChildren() {
		return super.getChildren().stream().map(FileObject.class::cast).collect(Collectors.toList());
	}

	abstract long getSize();
}

class Folder extends FileObject {
	public Folder(String name) {
		super(name);
	}

	@Override
	long getSize() {
		return getChildren().stream().mapToLong(FileObject::getSize).sum();
	}
}

class File extends FileObject {
	private final long size;

	public File(String name, long size) {
		super(name);
		this.size = size;
	}

	@Override
	long getSize() {
		return size;
	}
}
