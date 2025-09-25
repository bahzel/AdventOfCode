package solution.y2019;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import lombok.Getter;

@Getter
class IntCodeInterpreterThread implements Runnable {
	private final List<Long> register;
	private final BlockingQueue<Long> input;
	private final BlockingQueue<Long> output;
	private boolean finished = false;

	public IntCodeInterpreterThread(List<Long> register, BlockingQueue<Long> input, BlockingQueue<Long> output) {
		this.register = register;
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		IntCodeInterpreter.performComputation(register, input, output);
		finished = true;
	}
}
