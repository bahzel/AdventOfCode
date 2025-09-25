package solution.y2016;

import java.util.concurrent.atomic.AtomicInteger;

import utils.soution.InstructionSolution;

public class Day7_2 extends InstructionSolution<SSLAddess, AtomicInteger> {
	public static void main(String[] args) {
		new Day7_2().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected SSLAddess transformInstruction(String instruction) {
		return new SSLAddess(instruction);
	}

	@Override
	protected boolean performInstruction(SSLAddess ipAddress, AtomicInteger atomicInteger) {
		if (ipAddress.supportsSSL()) {
			atomicInteger.incrementAndGet();
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}

class SSLAddess extends IPAddress {
	public SSLAddess(String input) {
		super(input);
	}

	public boolean supportsSSL() {
		for (var address : getAddresses()) {
			for (int i = 0; i < address.length() - 2; i++) {
				if (address.charAt(i) != address.charAt(i + 1) && address.charAt(i) == address.charAt(i + 2)
						&& hypernetSequencesContains(
								"" + address.charAt(i + 1) + address.charAt(i) + address.charAt(i + 1))) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean hypernetSequencesContains(String bab) {
		return getHypernetSequences().stream().anyMatch(sequence -> sequence.contains(bab));
	}
}