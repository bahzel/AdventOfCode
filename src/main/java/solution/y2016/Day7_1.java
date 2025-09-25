package solution.y2016;

import lombok.Getter;
import utils.InstructionSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day7_1 extends InstructionSolution<IPAddress, AtomicInteger> {
	public static void main(String[] args) {
		new Day7_1().solve();
	}

	@Override
	protected AtomicInteger initializeValue() {
		return new AtomicInteger();
	}

	@Override
	protected IPAddress transformInstruction(String instruction) {
		return new IPAddress(instruction);
	}

	@Override
	protected boolean performInstruction(IPAddress ipAddress, AtomicInteger atomicInteger) {
		if (ipAddress.supportsTLS()) {
			atomicInteger.incrementAndGet();
		}

		return false;
	}

	@Override
	protected String getSolution(AtomicInteger atomicInteger) {
		return atomicInteger.toString();
	}
}

@Getter
class IPAddress {
	private final List<String> addresses = new ArrayList<>();
	private final List<String> hypernetSequences = new ArrayList<>();

	public IPAddress(String input) {
		var parts = input.split("[\\[\\]]");
		for (int i = 0; i < parts.length; i++) {
			if (i % 2 == 0) {
				addresses.add(parts[i]);
			} else {
				hypernetSequences.add(parts[i]);
			}
		}
	}

	public boolean supportsTLS() {
		return addresses.stream().anyMatch(IPAddress::hasABBA) && hypernetSequences.stream()
																				   .noneMatch(IPAddress::hasABBA);
	}

	private static boolean hasABBA(String address) {
		for (int i = 0; i < address.length() - 3; i++) {
			if (address.charAt(i) != address.charAt(i + 1) && address.charAt(i) == address.charAt(i + 3)
					&& address.charAt(i + 1) == address.charAt(i + 2)) {
				return true;
			}
		}
		return false;
	}
}