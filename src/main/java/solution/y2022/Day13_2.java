package solution.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utils.soution.MapSolution;

public class Day13_2 extends MapSolution<List<Packet>> {
	public static void main(String[] args) {
		new Day13_2().solve();
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
		transformInstruction("[[2]]", packets);
		var deviderPacket1 = packets.getLast();
		transformInstruction("[[6]]", packets);
		var deviderPacket2 = packets.getLast();

		var sortedPackets = packets.stream().sorted().toList();

		return (sortedPackets.indexOf(deviderPacket1) + 1) * (sortedPackets.indexOf(deviderPacket2) + 1) + "";
	}
}
