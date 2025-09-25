package solution.y2019;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5_2Test {
	@Test
	public void test() {
		assertThat(new Day5_2().disableLog().solve()).isEqualTo("5000972");
	}

	@Test
	public void equalTo8PositionModeTrue() {
		assertThat(new Day5_2(List.of("3,9,8,9,10,9,4,9,99,-1,8"), 8).disableLog().solve()).isEqualTo("1");
	}

	@Test
	public void equalTo8PositionModeFalse() {
		assertThat(new Day5_2(List.of("3,9,8,9,10,9,4,9,99,-1,8"), 7).disableLog().solve()).isEqualTo("0");
	}

	@Test
	public void lessThan8PositionModeTrue() {
		assertThat(new Day5_2(List.of("3,9,7,9,10,9,4,9,99,-1,8"), 7).disableLog().solve()).isEqualTo("1");
	}

	@Test
	public void lessThan8PositionModeFalse() {
		assertThat(new Day5_2(List.of("3,9,7,9,10,9,4,9,99,-1,8"), 8).disableLog().solve()).isEqualTo("0");
	}

	@Test
	public void equalTo8ImmediateModeTrue() {
		assertThat(new Day5_2(List.of("3,3,1108,-1,8,3,4,3,99"), 8).disableLog().solve()).isEqualTo("1");
	}

	@Test
	public void equalTo8ImmediateModeFalse() {
		assertThat(new Day5_2(List.of("3,3,1108,-1,8,3,4,3,99"), 7).disableLog().solve()).isEqualTo("0");
	}

	@Test
	public void lessThan8ImmediateModeTrue() {
		assertThat(new Day5_2(List.of("3,3,1107,-1,8,3,4,3,99"), 7).disableLog().solve()).isEqualTo("1");
	}

	@Test
	public void lessThan8ImmediateModeFalse() {
		assertThat(new Day5_2(List.of("3,3,1107,-1,8,3,4,3,99"), 8).disableLog().solve()).isEqualTo("0");
	}

	@Test
	public void equalTo0PositionModeTrue() {
		assertThat(new Day5_2(List.of("3,12,6,12,15,1,13,14,13,4,13,99,0,0,1,9"), 0).disableLog().solve()).isEqualTo(
				"0");
	}

	@Test
	public void equalTo0PositionModeFalse() {
		assertThat(new Day5_2(List.of("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"), -1).disableLog().solve()).isEqualTo(
				"1");
	}

	@Test
	public void equalTo0ImmediateModeTrue() {
		assertThat(new Day5_2(List.of("3,3,1105,-1,9,1101,0,0,12,4,12,99,1"), 0).disableLog().solve()).isEqualTo("0");
	}

	@Test
	public void equalTo0ImmediateModeFalse() {
		assertThat(new Day5_2(List.of("3,3,1105,-1,9,1101,0,0,12,4,12,99,1"), -1).disableLog().solve()).isEqualTo("1");
	}

	@Test
	public void checkAround8Smaller() {
		assertThat(new Day5_2(
				List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"),
				7).disableLog().solve()).isEqualTo("999");
	}

	@Test
	public void checkAround8Equal() {
		assertThat(new Day5_2(
				List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"),
				8).disableLog().solve()).isEqualTo("1000");
	}

	@Test
	public void checkAround8Bigger() {
		assertThat(new Day5_2(
				List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"),
				9).disableLog().solve()).isEqualTo("1001");
	}
}
