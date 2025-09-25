package solution.y2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.Solution;

public class Day16_1 extends Solution {
	public static void main(String[] args) {
		new Day16_1().solve();
	}

	@Override
	protected String doSolve() {
		var packet = StringUtils.leftPad(new BigInteger(input.getFirst(), 16).toString(2),
				input.getFirst().length() * 4, "0");
		return createPacket(packet, 0).getLeft().getVersion() + "";
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
			packet = new OperatorPacket(version, type);
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

@AllArgsConstructor
@Getter
abstract class Packet {
	private final int version;
	private final int type;
	private final List<Packet> subPackets = new ArrayList<>();

	public long getVersion() {
		long version = this.version;
		for (var subPacket : subPackets) {
			version += subPacket.getVersion();
		}
		return version;
	}

	public abstract long getValue();
}

@Getter
class LiteralPacket extends Packet {
	private final long value;

	public LiteralPacket(int version, int type, long value) {
		super(version, type);
		this.value = value;
	}
}

class OperatorPacket extends Packet {
	public OperatorPacket(int version, int type) {
		super(version, type);
	}

	@Override
	public long getValue() {
		throw new NotImplementedException();
	}
}
