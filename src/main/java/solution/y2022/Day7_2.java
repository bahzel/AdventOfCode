package solution.y2022;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import utils.soution.InstructionSolution;

public class Day7_2 extends InstructionSolution<String, AtomicReference<FileObject>> {
	public static void main(String[] args) {
		new Day7_2().solve();
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

		var amountMissing = 30000000 - 70000000 + parent.getSize();

		var minimumAmount = Long.MAX_VALUE;
		Queue<FileObject> queue = new LinkedList<>();
		queue.add(parent);
		while (!queue.isEmpty()) {
			var object = queue.poll();
			if (object instanceof File) {
				continue;
			}

			var size = object.getSize();
			if (size >= amountMissing) {
				minimumAmount = Math.min(minimumAmount, size);
				queue.addAll(object.getChildren());
			}
		}

		return minimumAmount + "";
	}
}
