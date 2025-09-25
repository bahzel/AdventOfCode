package solution.y2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import utils.soution.InstructionSolution;

public class Day3_1 extends InstructionSolution<Pair<List<String>, List<String>>, AtomicInteger> {
	public static void main(String[] args) {
		new Day3_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected Pair<List<String>, List<String>> transformInstruction(String instruction) {
		return Pair.of(Arrays.asList(instruction.substring(0, instruction.length() / 2).split("")),
				Arrays.asList(instruction.substring(instruction.length() / 2).split("")));
	}

	@Override
	protected boolean performInstruction(Pair<List<String>, List<String>> listListPair, AtomicInteger atomicInteger) {
		var wrongItem = new ArrayList<>(listListPair.getLeft());
		wrongItem.retainAll(listListPair.getRight());
		atomicInteger.addAndGet(
				wrongItem.getFirst().charAt(0) - (StringUtils.isAllUpperCase(wrongItem.getFirst()) ? 39 : 97) + 1);

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}
