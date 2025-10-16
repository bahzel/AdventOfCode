package solution.y2023;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day2_1 extends InstructionSolution<Pair<Integer, int[]>, AtomicInteger> {
	public static void main(String[] args) {
		new Day2_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected Pair<Integer, int[]> transformInstruction(String instruction) {
		var instructions = instruction.split(": ");
		var id = instructions[0].split(" ")[1];
		var cubes = instructions[1].replace(",", "").replace(";", "").split(" ");
		var cubeArray = new int[3];
		for (var i = 0; i < cubes.length; i = i + 2) {
			switch (cubes[i + 1]) {
			case "red":
				cubeArray[0] = Math.max(cubeArray[0], Integer.parseInt(cubes[i]));
				break;
			case "green":
				cubeArray[1] = Math.max(cubeArray[1], Integer.parseInt(cubes[i]));
				break;
			case "blue":
				cubeArray[2] = Math.max(cubeArray[2], Integer.parseInt(cubes[i]));
				break;
			default:
				throw new IllegalArgumentException("Invalid cube entry: " + cubes[i]);
			}
		}

		return Pair.of(Integer.parseInt(id), cubeArray);
	}

	@Override
	protected boolean performInstruction(Pair<Integer, int[]> integerPair, AtomicInteger atomicInteger) {
		if (integerPair.getValue()[0] <= 12 && integerPair.getValue()[1] <= 13 && integerPair.getValue()[2] <= 14) {
			atomicInteger.addAndGet(integerPair.getKey());
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}