package solution.y2016;

import java.util.concurrent.atomic.AtomicLong;

import utils.soution.InstructionSolution;

public class Day4_2 extends InstructionSolution<DecryptableRoom, AtomicLong> {
	public static void main(String[] args) {
		new Day4_2().solve();
	}

	@Override
	protected AtomicLong initializeValue() {
		return new AtomicLong();
	}

	@Override
	protected DecryptableRoom transformInstruction(String instruction) {
		return new DecryptableRoom(instruction);
	}

	@Override
	protected boolean performInstruction(DecryptableRoom decryptableRoom, AtomicLong atomicLong) {
		if ("northpole object storage".equals(decryptableRoom.decrypt())) {
			atomicLong.set(decryptableRoom.getSectorId());
			return true;
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicLong atomicLong) {
		return atomicLong.toString();
	}
}

class DecryptableRoom extends EncryptedRoom {
	public DecryptableRoom(String input) {
		super(input);
	}

	public String decrypt() {
		var solution = new StringBuilder();

		for (char c : getName().toCharArray()) {
			if (c == '-') {
				solution.append(" ");
			} else {
				solution.append(rotate(c));
			}
		}

		return solution.toString();
	}

	private char rotate(char c) {
		char rotated = c;
		rotated += (char) (getSectorId() % 26);
		if (rotated > 'z') {
			rotated -= 26;
		}
		return rotated;
	}
}