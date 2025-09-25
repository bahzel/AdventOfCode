package solution.y2016;

import lombok.Getter;
import utils.soution.InstructionSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day4_1 extends InstructionSolution<EncryptedRoom, AtomicLong> {
	public static void main(String[] args) {
		new Day4_1().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected EncryptedRoom transformInstruction(String instruction) {
		return new EncryptedRoom(instruction);
	}

	@Override
	protected boolean performInstruction(EncryptedRoom encryptedRoom, AtomicLong atomicLong) {
		if (encryptedRoom.isValid()) {
			atomicLong.addAndGet(encryptedRoom.getSectorId());
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}

class EncryptedRoom {
	@Getter
	private final String name;
	private final String checkSum;
	@Getter
	private final long sectorId;

	public EncryptedRoom(String input) {
		name = input.substring(0, input.lastIndexOf('-'));
		checkSum = input.substring(0, input.length() - 1).split("\\[")[1];
		sectorId = Long.parseLong(input.substring(input.lastIndexOf('-') + 1, input.indexOf('[')));
	}

	public boolean isValid() {
		return Objects.equals(computeChecksum(), checkSum);
	}

	private String computeChecksum() {
		Map<Character, Integer> characterMap = new HashMap<>();

		for (char c : name.replace("-", "").toCharArray()) {
			if (characterMap.containsKey(c)) {
				characterMap.put(c, characterMap.get(c) + 1);
			} else {
				characterMap.put(c, 1);
			}
		}

		return characterMap.entrySet().stream().sorted((e1, e2) -> {
			if (Objects.equals(e1.getValue(), e2.getValue())) {
				return Character.compare(e1.getKey(), e2.getKey());
			} else {
				return Integer.compare(e2.getValue(), e1.getValue());
			}
		}).map(Map.Entry::getKey).map(Object::toString).collect(Collectors.joining()).substring(0, 5);
	}
}