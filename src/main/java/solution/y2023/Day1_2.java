package solution.y2023;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day1_2 extends InstructionSolution<String, AtomicInteger> {
	private final Map<String, Integer> numberMap = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six",
			6, "seven", 7, "eight", 8, "nine", 9);

	public static void main(String[] args) {
		new Day1_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String instruction, AtomicInteger atomicInteger) {
		var firstIndex = 0;
		for (var i = 0; i < instruction.length(); i++) {
			if (Character.isDigit(instruction.charAt(i))) {
				firstIndex = i;
				break;
			}
		}
		for (var stringNumber : numberMap.keySet()) {
			var index = instruction.indexOf(stringNumber);
			if (index != -1) {
				firstIndex = Math.min(firstIndex, index);
			}
		}

		var lastIndex = 0;
		for (var i = instruction.length() - 1; i >= 0; i--) {
			if (Character.isDigit(instruction.charAt(i))) {
				lastIndex = i;
				break;
			}
		}
		for (var stringNumber : numberMap.keySet()) {
			var index = instruction.lastIndexOf(stringNumber);
			if (index != -1) {
				lastIndex = Math.max(lastIndex, index);
			}
		}

		var number = "";
		if (Character.isDigit(instruction.charAt(firstIndex))) {
			number += instruction.charAt(firstIndex);
		} else {
			for (var stringNumber : numberMap.keySet()) {
				if (instruction.startsWith(stringNumber, firstIndex)) {
					number += numberMap.get(stringNumber);
					break;
				}
			}
		}
		if (Character.isDigit(instruction.charAt(lastIndex))) {
			number += instruction.charAt(lastIndex);
		} else {
			for (var stringNumber : numberMap.keySet()) {
				if (instruction.startsWith(stringNumber, lastIndex)) {
					number += numberMap.get(stringNumber);
					break;
				}
			}
		}
		atomicInteger.addAndGet(Integer.parseInt(number));

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}