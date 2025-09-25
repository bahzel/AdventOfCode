package solution.y2021;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3_1Test {
	@Test
	public void test() {
		assertThat(new Day3_1().disableLog().solve()).isEqualTo("2595824");
	}
}
