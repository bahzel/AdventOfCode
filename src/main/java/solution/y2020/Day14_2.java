package solution.y2020;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day14_2 extends InstructionSolution<String, MutablePair<Map<Long, Long>, List<String>>> {
	public static void main(String[] args) {
		new Day14_2().solve();
	}

	@Override
	protected MutablePair<Map<Long, Long>, List<String>> initializeValue() {
		return MutablePair.of(new HashMap<>(), null);
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction.replace("[", " ").replace("]", "");
	}

	@Override
	protected boolean performInstruction(String s, MutablePair<Map<Long, Long>, List<String>> mapListMutablePair) {
		var instructions = s.split(" ");
		if (s.startsWith("mask")) {
			mapListMutablePair.setRight(StringTransformer.splitString(instructions[2]));
		} else {
			var binaryNumber = StringUtils.leftPad(Long.toBinaryString(Long.parseLong(instructions[1])), 36, "0");
			Queue<StringBuilder> queue = new LinkedList<>();
			queue.add(new StringBuilder());

			while (!queue.isEmpty()) {
				var address = queue.poll();
				if (address.length() == 36) {
					mapListMutablePair	.getLeft()
										.put(Long.parseLong(address.toString(), 2), Long.parseLong(instructions[3]));
					continue;
				}

				if ("X".equals(mapListMutablePair.getRight().get(address.length()))) {
					queue.add(new StringBuilder(address).append("0"));
					queue.add(address.append("1"));
				} else if ("1".equals(mapListMutablePair.getRight().get(address.length()))) {
					queue.add(address.append("1"));
				} else {
					queue.add(address.append(binaryNumber.charAt(address.length())));
				}
			}
		}
		return false;
	}

	@Override
	protected String getSolution(MutablePair<Map<Long, Long>, List<String>> mapListMutablePair) {
		return mapListMutablePair.getLeft().values().stream().mapToLong(Long::longValue).sum() + "";
	}
}
