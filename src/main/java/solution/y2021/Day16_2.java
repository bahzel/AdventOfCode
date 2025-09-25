package solution.y2021;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import utils.soution.Solution;

public class Day16_2 extends Solution {
	public static void main(String[] args) {
		new Day16_2().solve();
	}

	@Override
	protected String doSolve() {
		var packet = StringUtils.leftPad(new BigInteger(input.getFirst(), 16).toString(2),
				input.getFirst().length() * 4, "0");
		return createPacket(packet, 0).getLeft().getValue() + "";
	}

	private static Pair<Packet, Integer> createPacket(String payload, int index) {
		Packet packet;
		var version = Integer.parseInt(payload.substring(index, index + 3), 2);
		var type = Integer.parseInt(payload.substring(index + 3, index + 6), 2);
		index += 6;

		if (type == 4) {
			StringBuilder value = new StringBuilder();
			while (payload.charAt(index) == '1') {
				value.append(payload, index + 1, index + 5);
				index += 5;
			}
			value.append(payload, index + 1, index + 5);
			index += 5;
			packet = new LiteralPacket(version, type, Long.parseLong(value.toString(), 2));
		} else {
			packet = switch (type) {
			case 0 -> new SumPacket(version, type);
			case 1 -> new ProductPacket(version, type);
			case 2 -> new MinimumPacket(version, type);
			case 3 -> new MaximumPacket(version, type);
			case 5 -> new GreaterPacket(version, type);
			case 6 -> new LessPacket(version, type);
			case 7 -> new EqualPacket(version, type);
			default -> throw new IllegalArgumentException("Unknown packet type: " + type);
			};
			var lengthType = Integer.parseInt(payload.substring(index, index + 1), 2);
			if (lengthType == 0) {
				var length = Integer.parseInt(payload.substring(index + 1, index + 16), 2);
				index += 16;
				var endOfPacket = index + length;
				while (index < endOfPacket) {
					var subPacket = createPacket(payload, index);
					index = subPacket.getRight();
					packet.getSubPackets().add(subPacket.getLeft());
				}
			} else if (lengthType == 1) {
				var count = Integer.parseInt(payload.substring(index + 1, index + 12), 2);
				index += 12;
				for (var i = 0; i < count; i++) {
					var subPacket = createPacket(payload, index);
					index = subPacket.getRight();
					packet.getSubPackets().add(subPacket.getLeft());
				}
			} else {
				throw new NumberFormatException();
			}
		}
		return Pair.of(packet, index);
	}
}

class SumPacket extends OperatorPacket {
	public SumPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().stream().mapToLong(Packet::getValue).sum();
	}
}

class ProductPacket extends OperatorPacket {
	public ProductPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().stream().mapToLong(Packet::getValue).reduce(1, Math::multiplyExact);
	}
}

class MinimumPacket extends OperatorPacket {
	public MinimumPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().stream().mapToLong(Packet::getValue).min().orElseThrow();
	}
}

class MaximumPacket extends OperatorPacket {
	public MaximumPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().stream().mapToLong(Packet::getValue).max().orElseThrow();
	}
}

class GreaterPacket extends OperatorPacket {
	public GreaterPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().getFirst().getValue() > getSubPackets().get(1).getValue() ? 1 : 0;
	}
}

class LessPacket extends OperatorPacket {
	public LessPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().getFirst().getValue() < getSubPackets().get(1).getValue() ? 1 : 0;
	}
}

class EqualPacket extends OperatorPacket {
	public EqualPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		return getSubPackets().getFirst().getValue() == getSubPackets().get(1).getValue() ? 1 : 0;
	}
}
