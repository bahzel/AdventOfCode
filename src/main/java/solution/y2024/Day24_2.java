package solution.y2024;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import utils.soution.Solution;

public class Day24_2 extends Solution {
	public static void main(String[] args) {
		new Day24_2().solve();
	}

	@Override
	protected String doSolve() {
		fixInput("hmt", "z18");
		fixInput("z27", "bfq");
		fixInput("z31", "hkh");
		fixInput("bng", "fjp");

		var gateMap = getGates();
		for (int i = 1; gateMap.containsKey("z" + StringUtils.leftPad(i + 1 + "", 2, "0")); i++) {
			checkStructureOfZ(gateMap, i);
		}
		return Stream.of("hmt", "z18", "z27", "bfq", "z31", "hkh", "bng", "fjp")
					 .sorted()
					 .collect(Collectors.joining(","));
	}

	private void fixInput(String input1, String input2) {
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).endsWith(input1)) {
				input.set(i, input.get(i).replace(input1, input2));
			} else if (input.get(i).endsWith(input2)) {
				input.set(i, input.get(i).replace(input2, input1));
			}
		}
	}

	private void checkStructureOfZ(Map<String, Gate> gates, int index) {
		print(index + ":");
		var indexName = StringUtils.leftPad(index + "", 2, "0");
		var gate = gates.get("z" + indexName);
		if (!(gate.getFunction() instanceof XorFunction)) {
			print(" Wrong Function");
		}
		if (isNotXorAtIndex(gate.getInput1(), index) && isNotXorAtIndex(gate.getInput2(), index)) {
			print(" Wrong Child");
		}
		println();
	}

	private boolean isNotXorAtIndex(Gate gate, int index) {
		var indexName = StringUtils.leftPad(index + "", 2, "0");
		if (!(gate.getFunction() instanceof XorFunction)) {
			return true;
		}
		return !(("x" + indexName).equals(gate.getInput1().getName()) && ("y" + indexName).equals(
				gate.getInput2().getName()) || ("x" + indexName).equals(gate.getInput2().getName()) && ("y"
				+ indexName).equals(gate.getInput1().getName()));
	}

	private Map<String, Gate> getGates() {
		var indexOfSeparator = input.indexOf("");

		var gates = new HashMap<String, Gate>();
		for (int i = indexOfSeparator + 1; i < input.size(); i++) {
			var instructions = input.get(i).split(" ");
			var gate = gates.computeIfAbsent(instructions[4], Gate::new);
			gate.setInput1(gates.computeIfAbsent(instructions[0], Gate::new));
			gate.setInput2(gates.computeIfAbsent(instructions[2], Gate::new));

			switch (instructions[1]) {
			case "AND":
				gate.setFunction(AndFunction.AND_FUNCTION);
				break;
			case "OR":
				gate.setFunction(OrFunction.OR_FUNCTION);
				break;
			case "XOR":
				gate.setFunction(XorFunction.XOR_FUNCTION);
				break;
			}
		}

		return gates;
	}
}

@Setter
@Getter
class Gate {
	private String name;
	private Gate input1;
	private Gate input2;
	private BiFunction<Boolean, Boolean, Boolean> function;

	public Gate(String name) {
		this.name = name;
	}
}

class AndFunction implements BiFunction<Boolean, Boolean, Boolean> {
	public static final AndFunction AND_FUNCTION = new AndFunction();

	private AndFunction() {
	}

	@Override
	public Boolean apply(Boolean aBoolean, Boolean aBoolean2) {
		return aBoolean && aBoolean2;
	}
}

class OrFunction implements BiFunction<Boolean, Boolean, Boolean> {
	public static final OrFunction OR_FUNCTION = new OrFunction();

	private OrFunction() {
	}

	@Override
	public Boolean apply(Boolean aBoolean, Boolean aBoolean2) {
		return aBoolean || aBoolean2;
	}
}

class XorFunction implements BiFunction<Boolean, Boolean, Boolean> {
	public static final XorFunction XOR_FUNCTION = new XorFunction();

	private XorFunction() {
	}

	@Override
	public Boolean apply(Boolean aBoolean, Boolean aBoolean2) {
		return aBoolean ^ aBoolean2;
	}
}
