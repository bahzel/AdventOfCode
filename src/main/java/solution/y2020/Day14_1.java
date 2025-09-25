package solution.y2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import utils.StringTransformer;
import utils.soution.InstructionSolution;

public class Day14_1 extends InstructionSolution<String, MutablePair<Map<Integer, Long>, List<String>>> {
	public static void main(String[] args) {
		new Day14_1().solve();
	}

	@Override
	protected MutablePair<Map<Integer, Long>, List<String>> initializeValue() {
		return MutablePair.of(new HashMap<>(), null);
	}

	@Override
	protected String transformInstruction(String instruction) {
		return instruction.replace("[", " ").replace("]", "");
	}

	@Override
	protected boolean performInstruction(String s, MutablePair<Map<Integer, Long>, List<String>> mapListMutablePair) {
		var instructions = s.split(" ");
		if (s.startsWith("mask")) {
			mapListMutablePair.setRight(StringTransformer.splitString(instructions[2]));
		} else {
			var binaryNumber = StringUtils.leftPad(Long.toBinaryString(Long.parseLong(instructions[3])), 36, "0");
			var maskedNumber = new StringBuilder();
			for (var i = 0; i < binaryNumber.length(); i++) {
				if ("X".equals(mapListMutablePair.getRight().get(i))) {
					maskedNumber.append(binaryNumber.charAt(i));
				} else {
					maskedNumber.append(mapListMutablePair.getRight().get(i));
				}
			}
			mapListMutablePair	.getLeft()
								.put(Integer.parseInt(instructions[1]), Long.parseLong(maskedNumber.toString(), 2));
		}
		return false;
	}

	@Override
	protected String getSolution(MutablePair<Map<Integer, Long>, List<String>> mapListMutablePair) {
		return mapListMutablePair.getLeft().values().stream().mapToLong(Long::longValue).sum() + "";
	}
}
