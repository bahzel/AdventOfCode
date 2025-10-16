package solution.y2023;

import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day2_2 extends InstructionSolution<int[], AtomicInteger> {
	public static void main(String[] args) {
		new Day2_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger(0);
	}

	@Override
	protected int[] transformInstruction(String instruction) {
		var cubes = instruction.split(": ")[1].replace(",", "").replace(";", "").split(" ");
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

		return cubeArray;
	}

	@Override
	protected boolean performInstruction(int[] integerArray, AtomicInteger atomicInteger) {
		atomicInteger.addAndGet(integerArray[0] * integerArray[1] * integerArray[2]);
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}