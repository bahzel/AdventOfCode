package solution.y2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9_1Test {
	@Test
	public void test() {
		assertThat(new Day9_1().disableLog().solve()).isEqualTo("3518157894");
	}

	@Test
	public void copyOfItself() {
		var register = new ArrayList<>(
				Arrays	.stream("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".split(","))
						.mapToLong(Long::parseLong)
						.boxed()
						.toList());

		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withOutput(output).performComputation();
		assertThat(output.stream().map(value -> value + "").collect(Collectors.joining(","))).isEqualTo(
				"109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99");
	}

	@Test
	public void output16DigitNumber() {
		var register = new ArrayList<>(Arrays	.stream("1102,34915192,34915192,7,4,7,99,0".split(","))
												.mapToLong(Long::parseLong)
												.boxed()
												.toList());

		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withOutput(output).performComputation();
		assertThat(output.poll()).isEqualTo(1219070632396864L);
	}

	@Test
	public void output1125899906842624() {
		var register = new ArrayList<>(
				Arrays.stream("104,1125899906842624,99".split(",")).mapToLong(Long::parseLong).boxed().toList());

		var output = new LinkedBlockingQueue<Long>();
		new IntCodeInterpreter().withRegister(register).withOutput(output).performComputation();
		assertThat(output.poll()).isEqualTo(1125899906842624L);
	}
}
