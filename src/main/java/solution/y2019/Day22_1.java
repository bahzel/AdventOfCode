package solution.y2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import utils.soution.InstructionSolution;

public class Day22_1
		extends InstructionSolution<Consumer<AtomicReference<List<Integer>>>, AtomicReference<List<Integer>>> {
	private final int DECK_SIZE = 10007;

	public static void main(String[] args) {
		new Day22_1().solve();
	}

	@Override
	protected AtomicReference<List<Integer>> initializeValue() {
		var deck = new ArrayList<Integer>(DECK_SIZE);
		for (int i = 0; i < DECK_SIZE; i++) {
			deck.add(i);
		}
		return new AtomicReference<>(deck);
	}

	@Override
	protected Consumer<AtomicReference<List<Integer>>> transformInstruction(String instruction) {
		var instructions = instruction.split(" ");
		switch (instructions[1]) {
		case "with":
			var offset = Integer.parseInt(instructions[3]);
			return stack -> {
				var position = 0;
				var newList = new ArrayList<>(Collections.nCopies(DECK_SIZE, 0));
				for (var card : stack.get()) {
					newList.set(position, card);
					position += offset;
					if (position >= DECK_SIZE) {
						position %= DECK_SIZE;
					}
				}
				stack.set(newList);
			};
		case "into":
			return stack -> stack.set(stack.get().reversed());
		default:
			var cut = Integer.parseInt(instructions[1]);
			while (cut < 0) {
				cut += DECK_SIZE;
			}
			while (cut >= DECK_SIZE) {
				cut -= DECK_SIZE;
			}
			var finalCut = cut;
			return stack -> {
				var newList = stack.get().subList(finalCut, DECK_SIZE);
				newList.addAll(stack.get().subList(0, finalCut));
				stack.set(newList);
			};
		}
	}

	@Override
	protected boolean performInstruction(Consumer<AtomicReference<List<Integer>>> atomicReferenceConsumer,
			AtomicReference<List<Integer>> listAtomicReference) {
		atomicReferenceConsumer.accept(listAtomicReference);
		return false;
	}

	@Override
	protected String getSolution(AtomicReference<List<Integer>> listAtomicReference) {
		return listAtomicReference.get().indexOf(2019) + "";
	}
}
