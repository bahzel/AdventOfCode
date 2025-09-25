package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.tuple.MutablePair;

import lombok.Getter;
import utils.soution.Solution;

import static org.awaitility.Awaitility.await;

public class Day23_2 extends Solution {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected String doSolve() {
		var register = new ArrayList<>(
				Arrays.stream(input.getFirst().split(",")).mapToLong(Long::parseLong).boxed().toList());
		var network = new ArrayList<IntCodeInterpreter>();
		var inputs = new HashMap<Long, InputQueueWithCounter>();
		var natPackage = new MutablePair<Long, Long>();

		for (var address = 0L; address < 50; address++) {
			var input = new InputQueueWithCounter();
			input.add(address);
			inputs.put(address, input);

			network.add(new IntCodeInterpreter().withRegister(new ArrayList<>(register))
												.withInput(input)
												.withOutput(new OutputQueueWithNat(inputs, natPackage)));
		}

		network.forEach(computer -> new Thread(computer::performComputation).start());

		var lastY = Long.MIN_VALUE;
		while (true) {
			await().until(() -> inputs.values().stream().noneMatch(input -> input.getMissCounter() < 10000));
			if (natPackage.getRight() == lastY) {
				network.forEach(IntCodeInterpreter::interrupt);
				return lastY + "";
			}

			lastY = natPackage.getRight();
			inputs.get(0L).add(natPackage.getLeft(), natPackage.getRight());
		}
	}
}

@Getter
class InputQueueWithCounter extends LinkedBlockingQueue<Long> {
	private int missCounter = 0;

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Long take() throws InterruptedException {
		synchronized (this) {
			if (super.isEmpty()) {
				missCounter++;
				return -1L;
			}
			missCounter = 0;
			return super.take();
		}
	}

	public boolean add(Long x, Long y) {
		synchronized (this) {
			add(x);
			add(y);
		}
		return true;
	}
}

class OutputQueueWithNat extends LinkedBlockingQueue<Long> {
	private final Map<Long, InputQueueWithCounter> inputs;
	private final List<Long> buffer = new ArrayList<>();
	private final MutablePair<Long, Long> natPackage;

	public OutputQueueWithNat(Map<Long, InputQueueWithCounter> inputs, MutablePair<Long, Long> natPackage) {
		this.inputs = inputs;
		this.natPackage = natPackage;
	}

	@Override
	public boolean add(Long aLong) {
		buffer.add(aLong);

		if (buffer.size() == 3) {
			if (buffer.getFirst() == 255L) {
				natPackage.setLeft(buffer.get(1));
				natPackage.setRight(buffer.get(2));
			} else if (buffer.getFirst() < 50) {
				var input = inputs.get(buffer.getFirst());
				input.add(buffer.get(1), buffer.get(2));
			}
			buffer.clear();
		}

		return true;
	}
}
