package solution.y2021;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day10_1 extends InstructionSolution<String, AtomicInteger> {
	public static void main(String[] args) {
		new Day10_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction;
	}

	@Override
	protected boolean performInstruction(String s, AtomicInteger atomicInteger) {
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
					atomicInteger.addAndGet(3);
					return false;
				}
				break;
			case ']':
				if (openingStack.pop() != '[') {
					atomicInteger.addAndGet(57);
					return false;
				}
				break;
			case '}':
				if (openingStack.pop() != '{') {
					atomicInteger.addAndGet(1197);
					return false;
				}
				break;
			case '>':
				if (openingStack.pop() != '<') {
					atomicInteger.addAndGet(25137);
					return false;
				}
				break;
			default:
				throw new IllegalStateException();
			}
		}
		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
