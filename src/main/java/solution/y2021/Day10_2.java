package solution.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utils.soution.InstructionSolution;

public class Day10_2 extends InstructionSolution<String, List<Long>> {
	public static void main(String[] args) {
		new Day10_2().solve();
	}

	@Override
	protected List<Long> initializeValue() {
		return new ArrayList<>();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String s, List<Long> atomicInteger) {
		var openingStack = new Stack<Character>();
		for (char c : s.toCharArray()) {
			switch (c) {
			case '(':
			case '[':
			case '{':
			case '<':
				openingStack.push(c);
				break;
			case ')':
				if (openingStack.pop() != '(') {
					return false;
				}
				break;
			case ']':
				if (openingStack.pop() != '[') {
					return false;
				}
				break;
			case '}':
				if (openingStack.pop() != '{') {
					return false;
				}
				break;
			case '>':
				if (openingStack.pop() != '<') {
					return false;
				}
				break;
			default:
				throw new IllegalStateException();
			}
		}

		var score = 0L;
		while (!openingStack.isEmpty()) {
			score *= 5;
			switch (openingStack.pop()) {
			case '(':
				score++;
				break;
			case '[':
				score += 2;
				break;
			case '{':
				score += 3;
				break;
			case '<':
				score += 4;
				break;
			default:
				throw new IllegalStateException();
			}
		}
		atomicInteger.add(score);

		return false;
	}

	@Override
	protected String getSolution(List<Long> atomicInteger) {
		var sortedSolutions = atomicInteger.stream().sorted().toList();
		return sortedSolutions.get(sortedSolutions.size() / 2) + "";
	}
}
