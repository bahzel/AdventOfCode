package solution.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.MapSolution;

public class Day13_1 extends MapSolution<List<Packet>> {
	public static void main(String[] args) {
		new Day13_1().solve();
	}

	@Override
	protected List<String> getInstructions(List<String> instructions) {
		instructions.removeIf(String::isEmpty);
		return instructions;
	}

	@Override
	protected List<Packet> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Packet> packets) {
		PacketList currentPacket = null;
		var stack = new Stack<PacketList>();
		var buffer = new StringBuilder();

		for (var i = 0; i < instruction.length(); i++) {
			switch (instruction.charAt(i)) {
			case '[':
				var nextPacket = new PacketList();
				if (currentPacket != null) {
					currentPacket.getChildren().add(nextPacket);
					stack.push(currentPacket);
				}
				currentPacket = nextPacket;
				break;
			case ']':
				if (!buffer.isEmpty()) {
					currentPacket.getChildren().add(new PacketValue(Integer.parseInt(buffer.toString())));
					buffer.setLength(0);
				}
				if (stack.isEmpty()) {
					packets.add(currentPacket);
					return;
				}
				currentPacket = stack.pop();
				break;
			case ',':
				if (!buffer.isEmpty()) {
					currentPacket.getChildren().add(new PacketValue(Integer.parseInt(buffer.toString())));
					buffer.setLength(0);
				}
				break;
			default:
				buffer.append(instruction.charAt(i));
			}
		}
	}

	@Override
	protected String computeSolution(List<Packet> packets) {
		var solution = 0;

		for (var i = 0; i < packets.size(); i += 2) {
			if (packets.get(i).compareTo(packets.get(i + 1)) <= 0) {
				solution += i / 2 + 1;
			}
		}

		return String.valueOf(solution);
	}
}

interface Packet extends Comparable<Packet> {
}

@Getter
class PacketList implements Packet {
	private final List<Packet> children = new ArrayList<>();

	public PacketList() {
	}

	public PacketList(PacketValue child) {
		children.add(child);
	}

	@Override
	public int compareTo(Packet o) {
		if (o instanceof PacketList otherList) {
			for (var i = 0; i < Math.max(children.size(), otherList.children.size()); i++) {
				if (i == children.size()) {
					return -1;
				} else if (i == otherList.children.size()) {
					return 1;
				}

				var difference = children.get(i).compareTo(otherList.children.get(i));
				if (difference != 0) {
					return difference;
				}
			}
		} else if (o instanceof PacketValue otherValue) {
			return compareTo(new PacketList(otherValue));
		} else {
			throw new IllegalStateException();
		}
		return 0;
	}
}

@AllArgsConstructor
class PacketValue implements Packet {
	private final int value;

	@Override
	public int compareTo(Packet o) {
		if (o instanceof PacketList otherList) {
			return new PacketList(this).compareTo(otherList);
		} else if (o instanceof PacketValue otherValue) {
			return Integer.compare(value, otherValue.value);
		} else {
			throw new IllegalStateException();
		}
	}
}
